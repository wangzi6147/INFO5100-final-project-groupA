Inside components, there are several basic class that will go through the whole project. please check below comments and see if they need to be modified.

| ClassName | Type  | Comments |
| ------ | ------ | ------ |
| Address | class |  |
| BodyType | Enum | SUV, CAR, TRUCK, VAN are some type that i found in source data. add if you found some else |
| Dealer | class | should not store all the data in this class. we should implement some function in 'service' part to get the data |
| Special | class | see below enum, together with a parameter, we can easily describe to what granularity and target a dealer want to give |
| SpecialScope | Enum | I defined a scope enum, to help better implemation of scope applied |
| Vehicle | class |  |




