package dao;
import dto.*;

import java.sql.*;
import java.util.*;

public class VehicleQuery {

	Connection conn;
	Statement stmt = null;
	public VehicleQuery() throws SQLException{
		 conn = new DBconnect().connectDB();
		 stmt=conn.createStatement();
	}

    public List<Vehicle> getVehiclesByFilter(VehicleFilterSelected parameter) throws SQLException{
    	List<Vehicle> vehicles= new ArrayList();
    	String sql=generateSelectedSql(parameter);
    	ResultSet rs= stmt.executeQuery(sql);
    	while(rs.next()){
    		int id=rs.getInt("id");
    		int dealerId=rs.getInt("dealerID");
    		Vehicle v=new Vehicle(String.valueOf(id),String.valueOf(dealerId));
    		v.setYear(String.valueOf(rs.getInt("year")));
    		v.setBrand(rs.getString("brand"));
    		v.setModel(rs.getString("model"));
    		boolean isNew=true;
    		if(rs.getInt("isNew")==0) isNew=false;
    		v.setNewOrUsed(isNew);
    		v.setPrice(String.valueOf(rs.getString("price")));
    		v.setExteriorColor(rs.getString("exColor"));
    		v.setInteriorColor(rs.getString("inColor"));
    		v.setBodyType(getBodyType(rs.getString("type")));
    		List<String> features=new ArrayList();
    		features.add(rs.getString("features"));
    		v.setFeatures(features);
    		v.setMiles(String.valueOf(rs.getString("miles")));
    		List<String> images=new ArrayList();
    		images.add(rs.getString("images"));
    		v.setImages(images);
    		
    		vehicles.add(v);
    	}
    	return vehicles;
    }

    public VehicleFilterContent getVehicleFilterContent(VehicleFilterSelected parameter) throws SQLException{
        //@todo select distinct XXXX from vehicle.
    	VehicleFilterContent vehicleFilter= new VehicleFilterContent();
    	vehicleFilter.setDealerID(parameter.getDealerID());
    	
    	ResultSet yearsR= stmt.executeQuery(generateContentSql(parameter,"year"));
    	vehicleFilter.setYears(ResultSet2ListString(yearsR,"year"));
    	
    	ResultSet brandR= stmt.executeQuery(generateContentSql(parameter,"brand"));
    	vehicleFilter.setBrand(ResultSet2ListString(brandR,"brand"));
    	
    	ResultSet modelR= stmt.executeQuery(generateContentSql(parameter,"model"));
    	vehicleFilter.setModel(ResultSet2ListString(modelR,"model"));
    	
    	ResultSet isNewR= stmt.executeQuery(generateContentSql(parameter,"isNew"));
    	vehicleFilter.setIsNew(ResultSet2ListBoolean(isNewR,"isNew"));
    	
    	ResultSet priceR= stmt.executeQuery(generateContentSql(parameter,"price"));
    	vehicleFilter.setPrice(ResultSet2ListString(priceR,"price"));
    	
    	ResultSet exteriorColorR= stmt.executeQuery(generateContentSql(parameter,"exColor"));
    	vehicleFilter.setExteriorColor(ResultSet2ListString(exteriorColorR,"exColor"));
    	
    	ResultSet interiorColorR= stmt.executeQuery(generateContentSql(parameter,"inColor"));
    	vehicleFilter.setInteriorColor(ResultSet2ListString(interiorColorR,"inColor"));
    	
    	ResultSet typeR= stmt.executeQuery(generateContentSql(parameter,"type"));
    	vehicleFilter.setBodyType(ResultSet2ListBodyType(typeR,"type"));
    	
    	ResultSet milesR= stmt.executeQuery(generateContentSql(parameter,"miles"));
    	vehicleFilter.setMiles(ResultSet2ListString(milesR,"miles"));
    	
    	return vehicleFilter;
    }
    private List<String> ResultSet2ListString(ResultSet resultset,String columnLabel) throws SQLException{
    	List<String> list=new ArrayList();
    	while(resultset.next()){
    		list.add(resultset.getString(columnLabel));
    	}
    	return list;
    }
    
    private List<Boolean> ResultSet2ListBoolean(ResultSet resultset,String columnLabel) throws SQLException{
    	List<Boolean> list=new ArrayList();
    	while(resultset.next()){
    		list.add(resultset.getBoolean(columnLabel));
    	}
    	return list;
    }
    
    private List<BodyType> ResultSet2ListBodyType(ResultSet resultset,String columnLabel) throws SQLException{
    	List<BodyType> list=new ArrayList();
    	while(resultset.next()){
    		list.add(getBodyType(resultset.getString(columnLabel)));
    	}
    	return list;
    }
    
    private String generateContentSql(VehicleFilterSelected p,String str){
    	StringBuffer sql= new StringBuffer("select distinct ");
    	sql.append(str);
    	sql.append(" from vehicle where");
		if(p.getDealerID()!="") 
			sql.append(dealerIDSql(p.getDealerID()));
		if(p.getYears()!=null){
			if(str!="year"){
				sql.append(yearSql(p.getYears()));
			}
		}
		if(p.getBrand()!=null ){
			if(str!="brand"){
				sql.append(brandSql(p.getBrand()));
			}
		}
		if(p.getModel()!=null){
			if(str!="model"){
				sql.append(modelSql(p.getModel()));
			}
		}
		if(p.getIsNew()!=null){ // size=0 ||size=2 means don't care about new or old
			if(str!="isNew"){
				sql.append(isNewSql(p.getIsNew()));
			}
		}
		if(p.getPrice()!=null){ //show less than price 
			if(str!="price"){
				sql.append(priceSql(p.getPrice()));
			}
		}
		if(p.getExteriorColor()!=null){
			if(str!="exColor"){
				sql.append(exColorSql(p.getExteriorColor()));
			}			
		}
		if(p.getInteriorColor()!=null){
			if(str!="inColor"){
				sql.append(inColorSql(p.getInteriorColor()));
			}			
		}
		if(p.getBodyType()!=null){
			if(str!="type"){
				sql.append(typeSql(p.getBodyType()));
			}			
		}
		if(p.getMiles()!=null){
			if(str!="miles"){
				sql.append(milesSql(p.getMiles()));
			}
		}
		return sql.toString();
    }
	private String generateSelectedSql(VehicleFilterSelected p){
		StringBuffer sql= new StringBuffer("select * from vehicle where");
		if(p.getDealerID()!="")	sql.append(dealerIDSql(p.getDealerID()));
		if(p.getYears()!=null)	sql.append(yearSql(p.getYears()));
		if(p.getBrand()!=null)	sql.append(brandSql(p.getBrand()));
		if(p.getModel()!=null)	sql.append(modelSql(p.getModel()));
		if(p.getIsNew()!=null) 	sql.append(isNewSql(p.getIsNew()));
		if(p.getPrice()!=null) 	sql.append(priceSql(p.getPrice()));
		if(p.getExteriorColor()!=null) sql.append(exColorSql(p.getExteriorColor()));		
		if(p.getInteriorColor()!=null) sql.append(inColorSql(p.getInteriorColor()));		
		if(p.getBodyType()!=null) sql.append(typeSql(p.getBodyType()));		
		if(p.getMiles()!=null)	sql.append(milesSql(p.getMiles()));
		
		int pageSplit_start=(p.getPageNumber()-1)*20;
		sql.append(" limit ");
		sql.append(pageSplit_start);
		sql.append(" , ");
		sql.append(20);
		return sql.toString();
	}
    private BodyType getBodyType(String type){
    	BodyType bodyType=null;
    	switch(type){
		case "SUV":
			bodyType=BodyType.SUV;
			break;
		case "CAR":
			bodyType=BodyType.CAR;
			break;
		case "TRUCK":
			bodyType=BodyType.TRUCK;
			break;
		case "VAN":
			bodyType=BodyType.VAN;
			break;
    	}
    	return bodyType;
    }
    private String dealerIDSql(String dealer){
    	StringBuffer sql= new StringBuffer(" dealerID=");
    	sql.append(dealer);
		sql.append(" ");
		return sql.toString();
    }
    private String yearSql(List<String> years){
    	StringBuffer sql= new StringBuffer(" and ( ");
		for(int i=0;i<years.size();i++){
			sql.append("year=");
			sql.append(years.get(i));
			sql.append("||");
		}
		sql.delete(sql.length()-2, sql.length());
		sql.append(") ");
		return sql.toString();
    }
    private String brandSql(List<String> brand){
    	StringBuffer sql= new StringBuffer(" and ( ");
    	for(int i=0;i<brand.size();i++){
			sql.append("brand=\"");
			sql.append(brand.get(i));
			sql.append("\"||");
		}
		sql.delete(sql.length()-2, sql.length());
		sql.append(") ");
		return sql.toString();
    }
    private String modelSql(List<String> model){
    	StringBuffer sql= new StringBuffer(" and ( ");
    	for(int i=0;i<model.size();i++){
			sql.append("model=\"");
			sql.append(model.get(i));
			sql.append("\"||");
		}
		sql.delete(sql.length()-2, sql.length());
		sql.append(") ");
		return sql.toString();
    }
    private String isNewSql(List<Boolean> isNew){
    	StringBuffer sql= new StringBuffer(" and ");
		sql.append("isNew=");
		if(isNew.get(0) != null)
			sql.append(1);
		else 
			sql.append(0);
		sql.append(" ");
		return sql.toString();
    }
    private String priceSql(List<String> price){
    	StringBuffer sql= new StringBuffer();
    	if(price.size()==1){
			sql.append(" and price<=");
			sql.append(price.get(0));
		}
		sql.append(" ");
		return sql.toString();
    }
    private String exColorSql(List<String> color){
    	StringBuffer sql= new StringBuffer(" and ( ");
    	for(int i=0;i<color.size();i++){
			sql.append("exColor=\"");
			sql.append(color.get(i));
			sql.append("\"||");
		}
		sql.delete(sql.length()-2, sql.length());
		sql.append(") ");
		return sql.toString();
    }
    private String inColorSql(List<String> color){
    	StringBuffer sql= new StringBuffer(" and ( ");
    	for(int i=0;i<color.size();i++){
			sql.append("inColor=\"");
			sql.append(color.get(i));
			sql.append("\"||");
		}
		sql.delete(sql.length()-2, sql.length());
		sql.append(") ");
		return sql.toString();
    }
    private String typeSql(List<BodyType> bodytype){
    	StringBuffer sql= new StringBuffer(" and ( ");
		for(int i=0;i<bodytype.size();i++){
			sql.append("type=\"");
			sql.append(bodytype.get(i));
			sql.append("\"||");
		}
		sql.delete(sql.length()-2, sql.length());
		sql.append(") ");
		return sql.toString();
    }
    private String milesSql(List<String> miles){
    	StringBuffer sql= new StringBuffer(" ");
    	if(miles.size()==1){
			sql.append(" and miles<=");
			sql.append(miles.get(0));
		}
		sql.append(" ");
		return sql.toString();
    }
    


}
