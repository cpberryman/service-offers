RESTful API to allow a merchant to create, query and cancel offers.

Offers expire after a given time of months or days.
If an offer has expired this causes an error which is displayed in the response body.
Invalid offer data causes an error which is displayed in the response body.
Duplicate offer ids cause an error which is displayed in the response body.

To run the application:

- Follow the guide to install MongoDB on your machine: https://docs.mongodb.com/manual/installation/
- Ensure the mongod daemon is running.
- Use maven to build the application.

Example successful response for create, or find by id:

{
  "offer": [
    {
      "_id": "1",
      "price": 18.50,
      "currency": "JPY",
      "expired": false,
      "durationType": "MONTH",
      "durationNumber": 1
    }
  ]
}

Example successful response for find active offers:

{
  "offer": [
    {
      "_id": 1,
      "price": 18.50,
      "currency": "JPY",
      "expired": false,
      "durationType": "MONTH",
      "durationNumber": 2
    },
    {
      "_id": 2,
      "price": 49.99,
      "currency": "NOK",
      "expired": false,
      "durationType": "DAY",
      "durationNumber": 7
    },
    {
      "_id": 3,
      "price": 22.00,
      "currency": "GBP",
      "expired": false,
      "durationType": "MONTH",
      "durationNumber": 1
    }
  ]
}