# Apache Storm Hashtags Analyzer

An Apache storm analyzer for top 5 hashtags in each country.

# Work flow
* Tweets source is twitter api (twitter 4j) is used to get tweet body and the geolocation information.
* Tweets then parsed to get the hashtages and the country name I used mongoDB driver
    to get the nearest country to the given geolocation information data of countries, longitude and latitude are provided I get them from
    google [here](https://developers.google.com/public-data/docs/canonical/countries_csv).
* Tweets then counted using heavy hitters algorithm to get the top 5 occurrances in the stream.
* Finally visualize the result using d3 and flask python server.

You can find many maps code [here](http://datamaps.github.io/)

# Sample run

![First img](https://github.com/Arsanuos/ApacheStormHashtagsAnalyzer/blob/master/one.PNG)

![Second img](https://github.com/Arsanuos/ApacheStormHashtagsAnalyzer/blob/master/two.PNG)

![Third img](https://github.com/Arsanuos/ApacheStormHashtagsAnalyzer/blob/master/three.PNG)

![Fourth img](https://github.com/Arsanuos/ApacheStormHashtagsAnalyzer/blob/master/four.PNG)
