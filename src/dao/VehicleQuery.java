package dao;

import dto.*;

import java.sql.*;
import java.util.*;

public class VehicleQuery {

    Connection conn;
    Statement stmt;
    String dealerID;
    int pageSize = 20;

    private int pageCount = -1;
    private List<Vehicle> vehicles;
    private VehicleFilterContent vehicleFilterContent;


    public VehicleQuery() throws SQLException {

        conn = DBconnect.connectDB();
        stmt = conn.createStatement();
        vehicleFilterContent = new VehicleFilterContent();
        vehicles = new ArrayList<>();
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
        rs = stmt.executeQuery("SELECT DISTINCT year FROM " + cacheTableName);
        boolean[] yearGroupExsits = new boolean[7];
        while (rs.next()) {
            int year = rs.getInt("year");
            if (year < 2000) {
                yearGroupExsits[0] = true;
            } else if (year < 2010) {
                yearGroupExsits[1] = true;
            } else if (year < 2015) {
                yearGroupExsits[2] = true;
            } else if (year == 2015) {
                yearGroupExsits[3] = true;
            } else if (year == 2016) {
                yearGroupExsits[4] = true;
            } else if (year == 2017) {
                yearGroupExsits[5] = true;
            } else if (year == 2018) {
                yearGroupExsits[6] = true;
            }
        }
        List<String> yearList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (yearGroupExsits[i]) {
                switch (i) {
                    case 0:
                        yearList.add("Before 2000");
                        break;
                    case 1:
                        yearList.add("2000-2009");
                        break;
                    case 2:
                        yearList.add("2010-2014");
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        yearList.add((2012 + i) + "");
                        break;
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
        rs = stmt.executeQuery("SELECT DISTINCT price FROM " + cacheTableName);
        boolean[] priceGoupExsits = new boolean[9];
        while (rs.next()) {
            double price = rs.getDouble(1);
            if (price == 0.0) {
                priceGoupExsits[0] = true;
            } else if (price <= 10000.0) {
                priceGoupExsits[1] = true;
            } else if (price <= 20000.0) {
                priceGoupExsits[2] = true;
            } else if (price <= 30000.0) {
                priceGoupExsits[3] = true;
            } else if (price <= 40000.0) {
                priceGoupExsits[4] = true;
            } else if (price <= 50000.0) {
                priceGoupExsits[5] = true;
            } else if (price <= 100000.0) {
                priceGoupExsits[6] = true;
            } else if (price <= 200000.0) {
                priceGoupExsits[7] = true;
            } else {
                priceGoupExsits[8] = true;
            }
        }
        List<String> priceList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (priceGoupExsits[i]) {
                switch (i) {
                    case 0:
                        priceList.add("Negotiable");
                        break;
                    case 1:
                        priceList.add("1.0-10000.0");
                        break;
                    case 2:
                        priceList.add("10000.1-20000.0");
                        break;
                    case 3:
                        priceList.add("20000.1-30000.0");
                        break;
                    case 4:
                        priceList.add("30000.1-40000.0");
                        break;
                    case 5:
                        priceList.add("40000.1-50000.0");
                        break;
                    case 6:
                        priceList.add("50000.1-100000.0");
                        break;
                    case 7:
                        priceList.add("100000.1-200000.0");
                        break;
                    case 8:
                        priceList.add(">200000.0");
                        break;
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
        vehicleFilterContent.setExteriorColor(inColors);
        //BodyType
        rs = stmt.executeQuery("SELECT DISTINCT type FROM " + cacheTableName);
        List<BodyType> bodyTypes = new ArrayList<>();
        while (rs.next()) {
            bodyTypes.add(BodyType.valueOf(rs.getString(1)));
        }
        vehicleFilterContent.setBodyType(bodyTypes);

        //Miles  @todo should parse
        rs = stmt.executeQuery("SELECT DISTINCT miles FROM " + cacheTableName);
        List<String> miles = new ArrayList<>();
        while (rs.next()) {
            miles.add(rs.getString(1));
        }
        vehicleFilterContent.setMiles(miles);

        rs = stmt.executeQuery("SELECT * FROM " + cacheTableName + " ORDER BY " + sortTypeSql(p.getSortType()) + " LIMIT " + (p.getPageNumber() * pageSize) + " , " + pageSize);
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
        return sql.toString();
    }


    private String yearSql(List<String> years) {
        StringBuffer sql = new StringBuffer(" and (");
        for (String year : years) {
            if (sql.length() != " and (".length()) {
                sql.append("||");
            }
            if (year.contains("-")) {
                String[] y = year.split("-");
                sql.append(" (year>=" + y[0] + " and year<=" + y[1] + ")");
            } else if (year.contains("Before")) {
                sql.append(" year<2000 ");
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
                sql.append("price=0");
            } else {
                String[] p = price.split("-");
                sql.append(" (price>=" + p[0] + " and price<" + p[1] + ")");
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

    //@todo
    private String milesSql(List<String> miles) {
        StringBuffer sql = new StringBuffer(" and (");
        if (miles.size() == 1) {
            sql.append(" and miles<=");
            sql.append(miles.get(0));
        }
        sql.append(") ");
        return sql.toString();
    }

    private String sortTypeSql(SortType sortType) {

        if (sortType == null) {
            return null;
        }


        StringBuffer sql = new StringBuffer(" order by ");
        switch (sortType) {
            case PRICE_ASC:
                sql.append("price ");
                break;
            case PRICE_DSC:
                sql.append("price desc ");
                break;
            //	case DISCOUNT:
            //		sql.append("???? ");break;
            case YEAR:
                sql.append("year desc ");
                break;
            case MILES:
                sql.append("miles ");
                break;
            default:
                break;
        }
        return sql.toString();
    }


//    public List<Vehicle> getVehiclesByFilter(VehicleFilterSelected parameter) throws SQLException {
//        List<Vehicle> vehicles = new ArrayList<>();
//        String sql = generateSelectedSql(parameter);
//
//        System.out.println(sql);
//
//        ResultSet rs = stmt.executeQuery(sql);
//        while (rs.next()) {
//            int id = rs.getInt("id");
//            int dealerId = rs.getInt("dealerID");
//            Vehicle v = new Vehicle(String.valueOf(id), String.valueOf(dealerId));
//            v.setYear(String.valueOf(rs.getInt("year")));
//            v.setBrand(rs.getString("brand"));
//            v.setModel(rs.getString("model"));
//            boolean isNew = true;
//            if (rs.getInt("isNew") == 0) isNew = false;
//            v.setNewOrUsed(isNew);
//            v.setPrice(String.valueOf(rs.getString("price")));
//            v.setExteriorColor(rs.getString("exColor"));
//            v.setInteriorColor(rs.getString("inColor"));
//            v.setBodyType(BodyType.valueOf(rs.getString("type")));
//            List<String> features = new ArrayList<>();
//            features.add(rs.getString("features"));
//            v.setFeatures(features);
//            v.setMiles(String.valueOf(rs.getString("miles")));
//            List<String> images = new ArrayList<>();
//            images.add(rs.getString("images"));
//            v.setImages(images);
//
//            vehicles.add(v);
//        }
//        return vehicles;
//    }
//
//
//
//    public VehicleFilterContent getVehicleFilterContent(VehicleFilterSelected parameter) throws SQLException {
//
//        VehicleFilterContent vehicleFilter = new VehicleFilterContent();
//        vehicleFilter.setDealerID(parameter.getDealerID());
//
//        ResultSet yearsR = stmt.executeQuery(generateContentSql(parameter, "year"));
//        boolean[] yearGroupExsits = new boolean[7];
//        while (yearsR.next()) {
//            int year = yearsR.getInt("year");
//            if (year < 2000) {
//                yearGroupExsits[0] = true;
//            } else if (year < 2010) {
//                yearGroupExsits[1] = true;
//            } else if (year < 2015) {
//                yearGroupExsits[2] = true;
//            } else if (year == 2015) {
//                yearGroupExsits[3] = true;
//            } else if (year == 2016) {
//                yearGroupExsits[4] = true;
//            } else if (year == 2017) {
//                yearGroupExsits[5] = true;
//            } else if (year == 2018) {
//                yearGroupExsits[6] = true;
//            }
//        }
//        List<String> yearList = new ArrayList<>();
//        for (int i = 0; i < 7; i++) {
//            if (yearGroupExsits[i]) {
//
//                switch (i) {
//
//                    case 0:
//                        yearList.add("Before 2000");
//                        break;
//                    case 1:
//                        yearList.add("2000-2009");
//                        break;
//                    case 2:
//                        yearList.add("2010-2014");
//                        break;
//                    case 3:
//                    case 4:
//                    case 5:
//                    case 6:
//                        yearList.add((2012 + i) + "");
//                        break;
//                }
//
//            }
//        }
//
//        vehicleFilter.setYears(yearList);
//
//        ResultSet brandR = stmt.executeQuery(generateContentSql(parameter, "brand"));
//        vehicleFilter.setBrand(ResultSet2ListString(brandR, "brand"));
//
//        ResultSet modelR = stmt.executeQuery(generateContentSql(parameter, "model"));
//        vehicleFilter.setModel(ResultSet2ListString(modelR, "model"));
//
//        ResultSet isNewR = stmt.executeQuery(generateContentSql(parameter, "isNew"));
//        vehicleFilter.setIsNew(ResultSet2ListBoolean(isNewR, "isNew"));
//
//        ResultSet priceR = stmt.executeQuery(generateContentSql(parameter, "price"));
//
//        boolean[] priceGoupExsits = new boolean[9];
//        while (priceR.next()) {
//            double price = priceR.getDouble("price");
//            if (price == 0.0) {
//                priceGoupExsits[0] = true;
//            } else if (price <= 10000.0) {
//                priceGoupExsits[1] = true;
//            } else if (price <= 20000.0) {
//                priceGoupExsits[2] = true;
//            } else if (price <= 30000.0) {
//                priceGoupExsits[3] = true;
//            } else if (price <= 40000.0) {
//                priceGoupExsits[4] = true;
//            } else if (price <= 50000.0) {
//                priceGoupExsits[5] = true;
//            } else if (price <= 100000.0) {
//                priceGoupExsits[6] = true;
//            } else if (price <= 200000.0) {
//                priceGoupExsits[7] = true;
//            } else {
//                priceGoupExsits[8] = true;
//            }
//        }
//        List<String> priceList = new ArrayList<>();
//        for (int i = 0; i < 9; i++) {
//            if (priceGoupExsits[i]) {
//                switch (i) {
//                    case 0:
//                        priceList.add("Negotiable");
//                        break;
//                    case 1:
//                        priceList.add("1.0-10000.0");
//                        break;
//                    case 2:
//                        priceList.add("10000.1-20000.0");
//                        break;
//                    case 3:
//                        priceList.add("20000.1-30000.0");
//                        break;
//                    case 4:
//                        priceList.add("30000.1-40000.0");
//                        break;
//                    case 5:
//                        priceList.add("40000.1-50000.0");
//                        break;
//                    case 6:
//                        priceList.add("50000.1-100000.0");
//                        break;
//                    case 7:
//                        priceList.add("100000.1-200000.0");
//                        break;
//                    case 8:
//                        priceList.add(">200000.0");
//                        break;
//                }
//            }
//        }
//        vehicleFilter.setPrice(priceList);
//
//        ResultSet exteriorColorR = stmt.executeQuery(generateContentSql(parameter, "exColor"));
//        vehicleFilter.setExteriorColor(ResultSet2ListString(exteriorColorR, "exColor"));
//
//        ResultSet interiorColorR = stmt.executeQuery(generateContentSql(parameter, "inColor"));
//        vehicleFilter.setInteriorColor(ResultSet2ListString(interiorColorR, "inColor"));
//
//        ResultSet typeR = stmt.executeQuery(generateContentSql(parameter, "type"));
//        vehicleFilter.setBodyType(ResultSet2ListBodyType(typeR, "type"));
//
//        ResultSet milesR = stmt.executeQuery(generateContentSql(parameter, "miles"));
//        vehicleFilter.setMiles(ResultSet2ListString(milesR, "miles"));
//
//        return vehicleFilter;
//    }
//
//    private List<String> ResultSet2ListString(ResultSet resultset, String columnLabel) throws SQLException {
//        List<String> list = new ArrayList<>();
//        while (resultset.next()) {
//            list.add(resultset.getString(columnLabel));
//        }
//        return list;
//    }
//
//    private List<Boolean> ResultSet2ListBoolean(ResultSet resultset, String columnLabel) throws SQLException {
//        List<Boolean> list = new ArrayList<>();
//        while (resultset.next()) {
//            list.add(resultset.getBoolean(columnLabel));
//        }
//        return list;
//    }
//
//    private List<BodyType> ResultSet2ListBodyType(ResultSet resultset, String columnLabel) throws SQLException {
//        List<BodyType> list = new ArrayList<>();
//        while (resultset.next()) {
//            list.add(BodyType.valueOf(resultset.getString(columnLabel)));
//        }
//        return list;
//    }
//
//    private String generateContentSql(VehicleFilterSelected p, String str) {
//        StringBuffer sql = new StringBuffer("select distinct ");
//        sql.append(str);
//        sql.append(" from vehicle where dealerID=");
//
//        if (p.getDealerID() == null || p.getDealerID().isEmpty()) {
//            throw new IllegalArgumentException("Must provide Dealer ID ! ");
//        } else {
//            sql.append(p.getDealerID());
//        }
//        if (p.getYears() != null && !str.equals("year")) {
//            sql.append(yearSql(p.getYears()));
//        }
//        if (p.getBrand() != null && !str.equals("brand")) {
//            sql.append(brandSql(p.getBrand()));
//        }
//        if (p.getModel() != null && !str.equals("model")) {
//            sql.append(modelSql(p.getModel()));
//        }
//        if (p.getIsNew() != null && !str.equals("isNew")) {
//            sql.append(isNewSql(p.getIsNew()));     // size=0 ||size=2 means don't care about new or old
//        }
//        if (p.getPrice() != null && !str.equals("price")) {
//            sql.append(priceSql(p.getPrice()));     //show less than price
//        }
//        if (p.getExteriorColor() != null && !str.equals("exColor")) {
//            sql.append(exColorSql(p.getExteriorColor()));
//        }
//        if (p.getInteriorColor() != null && str.equals("inColor")) {
//            sql.append(inColorSql(p.getInteriorColor()));
//        }
//        if (p.getBodyType() != null && !str.equals("type")) {
//            sql.append(typeSql(p.getBodyType()));
//        }
//        if (p.getMiles() != null && !str.equals("miles")) {
//            sql.append(milesSql(p.getMiles()));
//        }
//        System.out.println(sql);
//        return sql.toString();
//    }

//    public String generateSelectedSql(VehicleFilterSelected p) {
//        StringBuffer sql = new StringBuffer("select * from vehicle where dealerID=");
//
//        if (p.getDealerID() == null || p.getDealerID().isEmpty()) {
//            throw new IllegalArgumentException("Must provide Dealer ID ! ");
//        } else {
//            sql.append( p.getDealerID());
//        }
//
//        if (p.getYears() != null) sql.append(yearSql(p.getYears()));
//        if (p.getBrand() != null) sql.append(brandSql(p.getBrand()));
//        if (p.getModel() != null) sql.append(modelSql(p.getModel()));
//        if (p.getIsNew() != null) sql.append(isNewSql(p.getIsNew()));
//        if (p.getPrice() != null) sql.append(priceSql(p.getPrice()));
//        if (p.getExteriorColor() != null) sql.append(exColorSql(p.getExteriorColor()));
//        if (p.getInteriorColor() != null) sql.append(inColorSql(p.getInteriorColor()));
//        if (p.getBodyType() != null) sql.append(typeSql(p.getBodyType()));
//        if (p.getMiles() != null) sql.append(milesSql(p.getMiles()));
//        if (p.getSortType() != null) sql.append(sortTypeSql(p.getSortType()));
//
//        int pageSplit_start = (p.getPageNumber() - 1) * pageSize;
//        sql.append(" limit ");
//        sql.append(pageSplit_start);
//        sql.append(" , ");
//        sql.append(pageSize);
//        return sql.toString();
//    }
//
//
//    private String yearSql(List<String> years) {
//        StringBuffer sql = new StringBuffer(" and (");
//        for (String year : years) {
//            if(sql.length() != " and (".length()){
//                sql.append("||");
//            }
//            if(year.contains("-")){
//                String[] y = year.split("-");
//                sql.append(" (year>=" + y[0] + " and year<=" + y[1] + ")");
//            }else if(year.contains("Before")){
//                sql.append( " year<" + year.split(" ")[1]);
//            }else{
//                sql.append(" year="  + year);
//            }
//        }
//        sql.append(") ");
//        return sql.toString();
//    }
//
//    private String brandSql(List<String> brand) {
//        StringBuffer sql = new StringBuffer(" and ( ");
//        for (int i = 0; i < brand.size(); i++) {
//            sql.append("brand=\"");
//            sql.append(brand.get(i));
//            sql.append("\"||");
//        }
//        sql.delete(sql.length() - 2, sql.length());
//        sql.append(") ");
//        return sql.toString();
//    }
//
//    private String modelSql(List<String> model) {
//        StringBuffer sql = new StringBuffer(" and ( ");
//        for (int i = 0; i < model.size(); i++) {
//            sql.append("model=\"");
//            sql.append(model.get(i));
//            sql.append("\"||");
//        }
//        sql.delete(sql.length() - 2, sql.length());
//        sql.append(") ");
//        return sql.toString();
//    }
//
//    private String isNewSql(List<Boolean> isNew) {
//        StringBuffer sql = new StringBuffer(" and ");
//        sql.append("isNew=");
//        if (isNew.get(0) != null)
//            sql.append(1);
//        else
//            sql.append(0);
//        sql.append(" ");
//        return sql.toString();
//    }
//
//
//    private String priceSql(List<String> prices) {
//        StringBuffer sql = new StringBuffer(" and (");
//
//        for(String price : prices) {
//            if (sql.length() != " and (".length()) {
//                sql.append("||");
//            }
//            if (price.equals("Negotiable")) {
//                sql.append("price=0");
//            } else {
//                String[] p = price.split("-");
//                sql.append(" (price>=" + p[0] + " and price<" + p[1]+")");
//            }
//        }
//        sql.append(") ");
//        return sql.toString();
//    }
//
//    private String exColorSql(List<String> color) {
//        StringBuffer sql = new StringBuffer(" and ( ");
//        for (int i = 0; i < color.size(); i++) {
//            sql.append("exColor=\"");
//            sql.append(color.get(i));
//            sql.append("\"||");
//        }
//        sql.delete(sql.length() - 2, sql.length());
//        sql.append(") ");
//        return sql.toString();
//    }
//
//    private String inColorSql(List<String> color) {
//        StringBuffer sql = new StringBuffer(" and ( ");
//        for (int i = 0; i < color.size(); i++) {
//            sql.append("inColor=\"");
//            sql.append(color.get(i));
//            sql.append("\"||");
//        }
//        sql.delete(sql.length() - 2, sql.length());
//        sql.append(") ");
//        return sql.toString();
//    }
//
//    private String typeSql(List<BodyType> bodytype) {
//        StringBuffer sql = new StringBuffer(" and ( ");
//        for (int i = 0; i < bodytype.size(); i++) {
//            sql.append("type='");
//            sql.append(bodytype.get(i));
//            sql.append("'||");
//        }
//        sql.delete(sql.length() - 2, sql.length());
//        sql.append(") ");
//        return sql.toString();
//    }
//
//    //@todo merge interval
//    private String milesSql(List<String> miles) {
//        StringBuffer sql = new StringBuffer(" and (");
//        if (miles.size() == 1) {
//            sql.append(" and miles<=");
//            sql.append(miles.get(0));
//        }
//        sql.append(") ");
//        return sql.toString();
//    }
//
//    private String sortTypeSql(SortType sortType) {
//        StringBuffer sql = new StringBuffer(" order by ");
//        switch (sortType) {
//            case PRICE_ASC:
//                sql.append("price ");
//                break;
//            case PRICE_DSC:
//                sql.append("price desc ");
//                break;
//            //	case DISCOUNT:
//            //		sql.append("???? ");break;
//            case YEAR:
//                sql.append("year desc ");
//                break;
//            case MILES:
//                sql.append("miles ");
//                break;
//            default:
//                break;
//        }
//        return sql.toString();
//    }

}
