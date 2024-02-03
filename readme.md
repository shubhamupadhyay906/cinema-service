#Cinema Service Api
<hr>
<hr>

<h1>Maven</h1>
mvn clean install
<hr>
<H1>Application Run</H1>

run application 
<hr>

<h1>Basic credential</h1>

<b>userName :user</b>

<b>password :password</>
<hr>
#Basic URL

Find all user: 

    {GET : http://localhost:8091/api/v1/user }

Response:

    {
        "id":1,
        "userName":"Naresh",
        "pwd":"abc1",
        "firstName":"nap2",
        "lastName":"nap2",
        "cinemaIFs":[{
                        "id":1,
                        "bookingDate":"2022-12-25T22:21:59.658+00:00",
                        "branchName":"EVA Mall vadodara",
                        "cinemaName":null,
                        "price":120.0,
                        "bookedSeat":1,
                        "screens":[ {
                                "id":1,
                                "type":"MY",
                                "movie":{
                                "id":2,
                                "title":"AR1",
                                "showCycle":true,
                                "releaseDate":"2022-12-12T22:21:59.658+00:00"
                                }
                        } ]
        } ]
    }
<hr>
Add User:

    { POST: http://localhost:8091/api/v1/user }
Request:

        {
            "user":{
            "userName": "Naresh",
            "pwd": "abc1",
            "firstName": "nap2",
            "lastName": "nap2",
            "cinemaIFs": [{
            "branchName":"EVA Mall vadodara",
            "price":"120.0",
            "parkingCharge":"30",
            "bookedSeat":1,
            "bookingDate":"2022-12-25T22:21:59.658",
            "screens":[
                            {
                                "type": "MY",
                                "movie": {
                                    "title": "AR1",
                                    "releaseDate": "2022-12-12T22:21:59.658",
                                    "showCycle": true
                                }
                            }
                        ]
                    }]
            }
        }
Response: