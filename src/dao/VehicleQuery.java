package dao;

import dto.*;

import java.sql.*;
import java.util.*;

public class VehicleQuery {

    Connection conn;
    Statement stmt;
    String dealerID;
    int pageSize = 20;

    int[][] yearChart = {
            {0, 2000},
            {2001, 2010},
            {2011, 2014},
            {2015, 2015},
            {2016, 2016},
            {2017, 2017},
            {2018, 2018},
    };

    double[][] priceChart = {
            {0.0, 0.0},
            {0.1, 9999.9},
            {10000.0, 19999.9},
            {20000.0, 29999.9},
            {30000.0, 39999.9},
            {40000.0, 49999.9},
            {50000.0, 99999.9},
            {100000.0, 199999.9},
            {200000.0, Double.MAX_VALUE}
    };

    int[][] milesChart = {
            {1, 4999},
            {5000, 9999},
            {10000, 19999},
            {20000, 29999},
            {30000, 39999},
            {40000, 49999},
            {50000, 59999},
            {60000, 69999},
            {70000, 79999},
            {80000, 89999},
            {90000, 99999},
            {100000, 149999},
            {150000, 199999},
            {200000, Integer.MAX_VALUE}
    };

    private int pageCount = -1;
    private List<Vehicle> vehicles;
    private VehicleFilterContent vehicleFilterContent;

    public VehicleQuery() throws SQLException {

        conn = DBconnect.connectDB();
        stmt = conn.createStatement();
        vehicleFilterContent = new VehicleFilterContent();
        vehicles = new ArrayList<>();
    }

    //A specified simplified query port ONLY used for apply specials.
    public List<Vehicle> getAllVehiclesByFilter(VehicleFilterSelected p) throws SQLException{

        List<Vehicle> res = new ArrayList<>();
        String sql = generateConditionSQL(p);
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            Vehicle v = new Vehicle(rs.getString("id"), dealerID);
            v.setYear(rs.getString("year"));
            v.setBrand(rs.getString("brand"));
            v.setModel(rs.getString("model"));
            v.setNewOrUsed(rs.getBoolean("isNew"));
            v.setPrice(String.valueOf(rs.getString("price")));
            v.setExteriorColor(rs.getString("exColor"));
            v.setInteriorColor(rs.getString("inColor"));
            v.setBodyType(BodyType.valueOf(rs.getString("type")));
            v.setMiles(String.valueOf(rs.getString("miles")));
            res.add(v);
        }
        return res;
    }

    public void Query(VehicleFilterSelected p) throws SQLException {

        String cacheTableName = "cache" + p.getDealerID();
        String sql = generateConditionSQL(p);
        stmt.executeUpdate("DROP TABLE IF EXISTS " + cacheTableName);
        stmt.execute("CREATE TEMPORARY TABLE " + cacheTableName + sql);
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + cacheTableName);
        if (rs.first()) {
            this.pageCount = (int) Math.ceil((double) rs.getInt(1) / pageSize);
        }
        //Year content
        List<String> yearList = new ArrayList<>();
        for (int[] y : yearChart) {
            rs = stmt.executeQuery("SELECT EXISTS (SELECT * FROM " + cacheTableName + " WHERE year<=" + y[1] + " AND year>=" + y[0] + ")");
            if (rs.first()) {
                if (rs.getInt(1) > 0) {
                    if (y[0] == 0) {
                        yearList.add("Before " + y[1]);
                    }
                    else if (y[0] == y[1]) {
                        yearList.add(y[0] + "");
                    } else {
                        yearList.add(y[0] + "--" + y[1]);
                    }
                }
            }
        }
        vehicleFilterContent.setYears(yearList);
        // Brand
        rs = stmt.executeQuery("SELECT DISTINCT brand FROM " + cacheTableName);
        List<String> brands = new ArrayList<>();
        while (rs.next()) {
            brands.add(rs.getString(1));
        }
        vehicleFilterContent.setBrand(brands);
        // Model
        rs = stmt.executeQuery("SELECT DISTINCT model FROM " + cacheTableName);
        List<String> models = new ArrayList<>();
        while (rs.next()) {
            models.add(rs.getString(1));
        }
        vehicleFilterContent.setModel(models);
        // isNew
        rs = stmt.executeQuery("SELECT DISTINCT isNew FROM " + cacheTableName);
        List<Boolean> conidtions = new ArrayList<>();
        while (rs.next()) {
            conidtions.add(rs.getBoolean(1));
        }
        vehicleFilterContent.setIsNew(conidtions);
        // Prices
        List<String> priceList = new ArrayList<>();
        for (double[] pricePair : priceChart) {
            rs = stmt.executeQuery("SELECT EXISTS (SELECT * FROM " + cacheTableName + " WHERE price<=" + pricePair[1] + " AND price>=" + pricePair[0] + ")");
            if (rs.first()) {
                if (rs.getInt(1) > 0) {
                    if (pricePair[1] == 0.0) {
                        priceList.add("Negotiable");
                    } else if (pricePair[1] == Double.MAX_VALUE) {
                        priceList.add("Above " + pricePair[0]);
                    } else priceList.add(pricePair[0] + "--" + pricePair[1]);
                }
            }
        }
        vehicleFilterContent.setPrice(priceList);
        //Excolor
        rs = stmt.executeQuery("SELECT DISTINCT exColor FROM " + cacheTableName);
        List<String> exColors = new ArrayList<>();
        while (rs.next()) {
            exColors.add(rs.getString(1));
        }
        vehicleFilterContent.setExteriorColor(exColors);
        //Incolor
        rs = stmt.executeQuery("SELECT DISTINCT inColor FROM " + cacheTableName);
        List<String> inColors = new ArrayList<>();
        while (rs.next()) {
            inColors.add(rs.getString(1));
        }
        vehicleFilterContent.setInteriorColor(inColors);
        //BodyType
        rs = stmt.executeQuery("SELECT DISTINCT type FROM " + cacheTableName);
        List<BodyType> bodyTypes = new ArrayList<>();
        while (rs.next()) {
            bodyTypes.add(BodyType.valueOf(rs.getString(1)));
        }
        vehicleFilterContent.setBodyType(bodyTypes);

        //Miles
        List<String> miles = new ArrayList<>();
        for (int[] m : milesChart) {
            rs = stmt.executeQuery("SELECT EXISTS (SELECT * FROM " + cacheTableName + " WHERE miles<=" + m[1] + " AND miles>=" + m[0] + ")");
            if (rs.first()) {
                if (rs.getInt(1) > 0) {
                    if (m[1] == Integer.MAX_VALUE) {
                        miles.add("Above " + m[0]);
                    } else miles.add(m[0] + "--" + m[1]);
                }
            }
        }
        vehicleFilterContent.setMiles(miles);

        rs = stmt.executeQuery("SELECT * FROM " + cacheTableName + sortTypeSql(p.getSortType()) + " LIMIT " + (p.getPageNumber() * pageSize) + " , " + pageSize);
        while (rs.next()) {
            Vehicle v = new Vehicle(rs.getString("id"), rs.getString("dealerID"));
            v.setYear(rs.getString("year"));
            v.setBrand(rs.getString("brand"));
            v.setModel(rs.getString("model"));
            boolean isNew = true;
            if (rs.getInt("isNew") == 0) isNew = false;
            v.setNewOrUsed(isNew);
            v.setPrice(String.valueOf(rs.getString("price")));
            v.setExteriorColor(rs.getString("exColor"));
            v.setInteriorColor(rs.getString("inColor"));
            v.setBodyType(BodyType.valueOf(rs.getString("type")));
            List<String> features = new ArrayList<>();
            features.add(rs.getString("features"));
            v.setFeatures(features);
            v.setMiles(String.valueOf(rs.getString("miles")));
            List<String> images = new ArrayList<>();
            images.add(rs.getString("images"));
            v.setImages(images);
            v.setFinalPrice(rs.getString("finalPrice"));
            v.setDiscountRate(rs.getString("discountRate"));
            String specials = rs.getString("specialIDs");
            List<String> specialIDs = (specials == null || specials.isEmpty()) ? new ArrayList<>() : Arrays.asList(specials.split(","));
            v.setSpecialIDs(specialIDs);
            vehicles.add(v);
        }

        stmt.executeUpdate("DROP TABLE IF EXISTS " + cacheTableName);
    }

    public List<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public VehicleFilterContent getVehicleFilterContent() {
        return vehicleFilterContent;
    }

    private String generateConditionSQL(VehicleFilterSelected p) {
        StringBuffer sql = new StringBuffer(" SELECT * FROM vehicle WHERE dealerID=");

        if (p.getDealerID() == null || p.getDealerID().isEmpty()) {
            throw new IllegalArgumentException("Must provide Dealer ID ! ");
        } else {
            sql.append(p.getDealerID());
        }
        if (p.getYears() != null) sql.append(yearSql(p.getYears()));
        if (p.getBrand() != null) sql.append(brandSql(p.getBrand()));
        if (p.getModel() != null) sql.append(modelSql(p.getModel()));
        if (p.getIsNew() != null) sql.append(isNewSql(p.getIsNew()));
        if (p.getPrice() != null) sql.append(priceSql(p.getPrice()));
        if (p.getExteriorColor() != null) sql.append(exColorSql(p.getExteriorColor()));
        if (p.getInteriorColor() != null) sql.append(inColorSql(p.getInteriorColor()));
        if (p.getBodyType() != null) sql.append(typeSql(p.getBodyType()));
        if (p.getMiles() != null) sql.append(milesSql(p.getMiles()));
        System.out.println(sql);
        return sql.toString();
    }

    private String yearSql(List<String> years) {
        StringBuffer sql = new StringBuffer(" and (");
        for (String year : years) {
            if (sql.length() != " and (".length()) {
                sql.append("||");
            }
            if (year.contains("-")) {
                String[] y = year.split("--");
                sql.append(" (year>=" + y[0] + " and year<=" + y[1] + ")");
            } else if (year.contains("Before")) {
                sql.append(" year<=" + yearChart[0][1]);
            } else {
                sql.append(" year=" + year);
            }
        }
        sql.append(") ");
        return sql.toString();
    }

    private String brandSql(List<String> brand) {
        StringBuffer sql = new StringBuffer(" and ( ");
        for (int i = 0; i < brand.size(); i++) {
            sql.append("brand=\"");
            sql.append(brand.get(i));
            sql.append("\"||");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(") ");
        return sql.toString();
    }

    private String modelSql(List<String> model) {
        StringBuffer sql = new StringBuffer(" and ( ");
        for (int i = 0; i < model.size(); i++) {
            sql.append("model=\"");
            sql.append(model.get(i));
            sql.append("\"||");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(") ");
        return sql.toString();
    }

    private String isNewSql(List<Boolean> isNew) {
        StringBuffer sql = new StringBuffer(" and ");
        sql.append("isNew=");
        if (isNew.get(0) != null)
            sql.append(1);
        else
            sql.append(0);
        sql.append(" ");
        return sql.toString();
    }

    private String priceSql(List<String> prices) {
        StringBuffer sql = new StringBuffer(" and (");

        for (String price : prices) {
            if (sql.length() != " and (".length()) {
                sql.append("||");
            }
            if (price.equals("Negotiable")) {
                sql.append(" price=" + priceChart[0][0]);
            } else if(price.contains("Above")){
                sql.append(" price>=" + priceChart[priceChart.length - 1][0]);
            }else{
                String[] p = price.split("--");
                sql.append(" (price>=" + p[0] + " and price<=" + p[1] + ")");
            }
        }
        sql.append(") ");
        return sql.toString();
    }

    private String exColorSql(List<String> color) {
        StringBuffer sql = new StringBuffer(" and ( ");
        for (int i = 0; i < color.size(); i++) {
            sql.append("exColor=\"");
            sql.append(color.get(i));
            sql.append("\"||");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(") ");
        return sql.toString();
    }

    private String inColorSql(List<String> color) {
        StringBuffer sql = new StringBuffer(" and ( ");
        for (int i = 0; i < color.size(); i++) {
            sql.append("inColor=\"");
            sql.append(color.get(i));
            sql.append("\"||");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(") ");
        return sql.toString();
    }

    private String typeSql(List<BodyType> bodytype) {
        StringBuffer sql = new StringBuffer(" and ( ");
        for (int i = 0; i < bodytype.size(); i++) {
            sql.append("type='");
            sql.append(bodytype.get(i));
            sql.append("'||");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(") ");
        return sql.toString();
    }

    private String milesSql(List<String> miles) {
        StringBuffer sql = new StringBuffer(" and (");
        for(String mile : miles){
            if (sql.length() != " and (".length()) {
                sql.append("||");
            }
            if(mile.contains("Above")){
                sql.append(" miles>=" + milesChart[milesChart.length - 1][0]);
            }else{
                String[] m = mile.split("--");
                sql.append(" (miles>=" + m[0] + " and miles<=" + m[1] + ")");
            }
        }

        sql.append(") ");
        return sql.toString();
    }

    private String sortTypeSql(SortType sortType) {

        if (sortType == null) {
            return "";
        }

        StringBuffer sql = new StringBuffer(" order by ");
        switch (sortType) {
            case PRICE_ASC:
                sql.append("price ");break;
            case PRICE_DSC:
                sql.append("price desc ");break;
            case DISCOUNT:
            		sql.append("discountRate desc ");break;
            case YEAR:
                sql.append("year desc ");break;
            case MILES:
                sql.append("miles ");break;
            // we can have a default sort. but not specified now.
        }
        return sql.toString();
    }

}
