these are some methods that can be used for basic querys from DB.
Note that these methods are not the query methods that will be used for interactive. 
I will write one or two class in 'service' package, which is a useable port for multi-filter and sort intention.

#   dealerQuery.class 
###  input a dealer id, return a dealer class which contains dealer basic infos.
    public Dealer findDealerByID(String id){

    }
###  input a city name, return all the dealers in such city.
    public List<Dealer> findDealersByCity(String city){

    }
###   input a postCode, return all the dealers nearby the postcode.
    public List<Dealer> findDealersByPostCode(int postCode){

    }
#   vehicleQuery.class 
###  input a vehicle id, return a vechile class which contains all infos.
    public Vehicle getVehicleByID(String id){

    }
###  input a dealer id, return all vehicleIDs that the dealer have.
    public List<String> getVehicleIDsByDealerID(String dealerID){

    }
###  input a city name, return all the vehicle ids  in such city.
    public List<String>getVehicleIDsByCity(String city){

    }
###   input a postCode, return all the vehicle ids nearby the postcode.
    public List<String> getVehicleIDsByType(BodyType type){

    }
    
#   specialQuery.class 
###  input a special id, return a special class which contains all infos.
    public Special getSpecialByID(String id){

    }
###  input a dealer id, return all specials that the dealer have.
    public List<Special> getSpecialsByDealerID(String dealerID){

    }
###  input a scope, return all the specials in such city.
    public List<Special> getSpecialsByScope(SpecialScope scope){

    }
###   input a date, return all the specials that are still activate.
    public List<Special> getSpecialsByDate(String date){

    }
