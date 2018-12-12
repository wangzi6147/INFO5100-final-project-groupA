package dao;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import dto.Dealer;
import dto.DealerQueryResponse;

public class DealerManagerImplementationTest {
	DealerManagerImplementation DMLtest= new DealerManagerImplementation();
	
	//by postcode
	@Test
	public void testSearchDealersIntInt() throws SQLException {
		 List<Dealer> dlist=DMLtest.searchDealers(23, 300).getDealerList();
		 for(Dealer d :dlist) {
			 assertEquals(23,Integer.parseInt(d.getZip()));
		 }
		
	}
// by name,address
	@Test
	public void testSearchDealersStringStringInt() throws SQLException {
		List<Dealer> dlist=DMLtest.searchDealers("gmps-jimmy","Seattle",1).getDealerList();
		for(Dealer d :dlist) {
			 assertEquals("gmps-jimmy",d.getName());
			 assertEquals("Seattle",d.getCity());
			 
		 }
		
	}

}
