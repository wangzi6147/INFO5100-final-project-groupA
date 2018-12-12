package dao;

import dto.*;

import java.sql.*;
import java.util.*;

public class VehicleManagerImpl implements VehicleManager {

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

    public VehicleManagerImpl() {
        conn = DBconnect.connectDB();
    }

    /**
     * Vehicle Query
     */
    public Vehicle findVehicleById(int vehicleId) throws SQLException {
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle WHERE id=" + vehicleId);
        if (rs.next()) {
            return generatVehicleFromResultSet(rs);
        } else {
            return new Vehicle("-1", "-1");
        }
    }

    private String checkIfNull(String column) {
        if (column == null)
            return "null";
        return column;
    }

    private Vehicle generatVehicleFromResultSet(ResultSet rs) {
        Vehicle v = null;
        try {
            v = new Vehicle(rs.getString("id"), "");
            v.setDealerID(rs.getString("dealerId"));
            v.setYear(rs.getString("year"));
            v.setBrand(rs.getString("brand"));
            v.setModel(rs.getString("model"));
            v.setNewOrUsed(rs.getBoolean("isNew"));
            v.setPrice(String.valueOf(rs.getString("price")));
            v.setExteriorColor(rs.getString("exColor"));
            v.setInteriorColor(rs.getString("inColor"));
            v.setBodyType(BodyType.valueOf(rs.getString("type")));
            v.setMiles(rs.getString("miles"));
            v.setFeatures(Arrays.asList(checkIfNull(rs.getString("features")).split("\\n")));
            v.setImages(Arrays.asList(checkIfNull(rs.getString("images")).split("\\n")));
        } catch (Exception e) {
            e.printStackTrace();
            v = new Vehicle("-1", "-1");
        }
        return v;
    }

    public List<Vehicle> findAllVehiclesByDealerId(String dealerId) throws SQLException {
        List<Vehicle> res = new ArrayList<>();
        this.dealerID = dealerId;
        String sql = "SELECT * FROM vehicle WHERE dealerID=" + dealerId;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        try {
            while (rs.next()) {
                Vehicle v = generatVehicleFromResultSet(rs);
                // check if v is invalid
                if (v.getId().equals("-1")) continue;
                res.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    //A specified simplified query port ONLY used for apply specials.
    public List<Vehicle> getAllVehiclesByFilter(VehicleFilterSelected p) throws SQLException {

        List<Vehicle> res = new ArrayList<>();
        String sql = generateConditionSQL(p);
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql.toString());
        while (rs.next()) {
            Vehicle v = generatVehicleFromResultSet(rs);
            res.add(v);
        }
        return res;
    }

    public void Query(VehicleFilterSelected p) throws SQLException {
        vehicleFilterContent = new VehicleFilterContent();
        vehicles = new ArrayList<>();
        String cacheTableName = "cache" + p.getDealerID();
        String sql = generateConditionSQL(p);
        stmt = conn.createStatement();
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
                    } else if (y[0] == y[1]) {
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
        List<String> conidtions = new ArrayList<>();
        while (rs.next()) {
            boolean isnew = rs.getBoolean(1);
            conidtions.add(isnew ? "New" : "Used");
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
        List<String> bodyTypes = new ArrayList<>();
        while (rs.next()) {
            bodyTypes.add(rs.getString(1));
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

        rs = stmt.executeQuery("SELECT * FROM " + cacheTableName + sortTypeSql(p.getSortType()) + " LIMIT " + ((p.getPageNumber() - 1) * pageSize) + " , " + pageSize);
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
            List<String> specialIDs = (List<String>) ((specials == null || specials.isEmpty()) ? new ArrayList<>() : Arrays.asList(specials.split(",")));
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

    private String pageSql(int PageNumber) {
        StringBuffer sql = new StringBuffer(" ");
        int pageSplit_start = (PageNumber - 1) * 20;
        sql.append(" limit ");
        sql.append(pageSplit_start);
        sql.append(" , ");
        sql.append(20);
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

    private String isNewSql(List<String> isNew) {
        if (isNew == null || isNew.isEmpty() || isNew.size() == 2) {
            return "";
        }
        String s = isNew.get(0).equals("New") ? "1" : "0";
        return " AND isNew=" + s;
    }

    private String priceSql(List<String> prices) {
        StringBuffer sql = new StringBuffer(" and (");

        for (String price : prices) {
            if (sql.length() != " and (".length()) {
                sql.append("||");
            }
            if (price.equals("Negotiable")) {
                sql.append(" price=" + priceChart[0][0]);
            } else if (price.contains("Above")) {
                sql.append(" price>=" + priceChart[priceChart.length - 1][0]);
            } else {
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

    private String typeSql(List<String> bodytype) {
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
        for (String mile : miles) {
            if (sql.length() != " and (".length()) {
                sql.append("||");
            }
            if (mile.contains("Above")) {
                sql.append(" miles>=" + milesChart[milesChart.length - 1][0]);
            } else {
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
                sql.append("price ");
                break;
            case PRICE_DSC:
                sql.append("price desc ");
                break;
            case DISCOUNT:
                sql.append("discountRate desc ");
                break;
            case YEAR:
                sql.append("year desc ");
                break;
            case MILES:
                sql.append("miles ");
                break;
            // we can have a default sort. but not specified now.
        }
        return sql.toString();
    }


    /**
     *  Maintain Vehicle
     */

    /**
     * method to Insert or Update Vehicle
     *
     * @param vehicle
     * @return
     */
    @Override
    public String maintainVehicle(Vehicle vehicle) throws SQLException {
        if (vehicle.getId() == null) {
            generateVehicleBySQL("INSERT INTO vehicle " +
                    "( year, brand, model, price, exColor, inColor, type, miles, images, dealerID, isNew, features ) " +
                    "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", vehicle);
            return getLastInsertID();
        } else {
            generateVehicleBySQL("UPDATE vehicle SET " +
                    "year = ?, brand = ?, model = ?, price = ?, exColor = ?, " +
                    "inColor = ?, type = ?, miles = ?, images = ?, dealerID = ?, isNew = ?, features = ? WHERE id = " + vehicle.getId(), vehicle);
            return vehicle.getId();
        }
    }

    private String getLastInsertID() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID();");
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            rs.close();
        }
        // when catch an exception or empty result set
        return "-1";
    }

    public void generateVehicleBySQL(String sql, Vehicle v) throws SQLException {

        PreparedStatement ps = conn.prepareStatement(sql);
        try {
            ps.setString(1, v.getYear());
            ps.setString(2, v.getBrand());
            ps.setString(3, v.getModel());
            ps.setString(4, v.getPrice());
            ps.setString(5, v.getExteriorColor());
            ps.setString(6, v.getInteriorColor());
            ps.setString(7, v.getBodyType() == null ? null : v.getBodyType().toString());
            ps.setString(8, v.getMiles());
            if (v.getImages() != null && !v.getImages().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String s : v.getImages())
                    sb.append(s + "\n");
                ps.setString(9, sb.toString()); // all images url compressed to one string.
            } else {
                ps.setString(9, "null");
            }
            ps.setString(10, v.getDealerID());
            ps.setBoolean(11, v.getIsNew());
            if (v.getFeatures() != null && !v.getFeatures().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String s : v.getFeatures())
                    sb.append(s + "\n");
                ps.setString(12, sb.toString());
            } else {
                ps.setString(12, "null");
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
        }
    }

    /**
     * method to delete vehicle
     *
     * @param vehicleId
     * @return
     */
    @Override
    public boolean deleteVehicle(String vehicleId) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(
                    "DELETE FROM vehicle WHERE id=" + vehicleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            preparedStatement.close();
        }
        return true;
    }

    public void updateFinalPriceAndDiscount(List<Vehicle> vehicles) throws SQLException {

        Statement stmt = conn.createStatement();
        StringBuffer sql = new StringBuffer("UPDATE vehicle SET finalPrice = CASE id ");
        for (Vehicle v : vehicles) {
            sql.append(" when " + v.getId() + " then " + v.getFinalPrice());
        }
        sql.append(" END, discountRate = CASE id");
        for (Vehicle v : vehicles) {
            sql.append(" when " + v.getId() + " then " + v.getDiscountRate());
        }
        sql.append(" END where id in (");
        for (Vehicle v : vehicles) {
            sql.append(v.getId() + ",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        System.out.println(sql);
        stmt.executeUpdate(sql.toString());
        stmt.close();
    }

}