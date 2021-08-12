var express = require('express');
var request = require('request');
var cheerio = require('cheerio');
const MongoClient = require('mongodb').MongoClient;
var app = express();


var collection;
var database;
var db_client;

//Make a connection to the mongo db database
MongoClient.connect(uri, { useNewUrlParser: true }, function(err, client) {
    if(err) {
        console.log('Error occurred while connecting to MongoDB Atlas...\n', err);
    }
    else{
        console.log('Connected to MongoDB Atlas!'); 
        app.listen(9000, function(){
            console.log("Server running on port # 9000");
            //server is running
        })	
        db_client = client;
        collection = client.db("news_database").collection("my_collection");
        // perform actions on the collection object
           
        console.log("Client closed");
    }
});




function getArticleData(){
    var results = [];

    return new Promise(function(resolve, reject){
        request('https://www.businesslive.co.za/bd/markets/', function (error, response, html) {
            if (!error && response.statusCode == 200) {
                var $ = cheerio.load(html);
                $("div.section-block").each(function(i, element){
                    var info = $(this).find("a");
                    var title = info.attr("title");
                    var url = info.attr("href");

                    var metaData = {
                        title: title,
                        link: url
                    }
                    results.push(metaData);	//Push the results into an array and display
                });

            }//end of if statement

            if(error){
                reject(error);
            }
            else{
                resolve(results);
            }

        });//end of request function
    });
}

function getArticleContent(contentURL){
    return new Promise(function(resolve, reject){
        request(contentURL, function (err, response, content_html){
            if(response != null && response != ""){
                var $ = cheerio.load(content_html); //Load the html tags and contents

                //Navigate through the html to find the contents				
                var title = $("div#content").find("div > div > div > div > div > h3").prev().text();
                var date = $("div.article-pub-date").contents().first().text(); //only gets the text & ignores anything else
                var content = $("div.text").text();

			    //Put the data into a JSON object
                var data = {
                    article_title: title,
                    article_date: date,
                    article_content: content
                }

                if(err){
                    reject(err);
                }
                else{                        
                    resolve(data); //Pass the data as a "synchronous" stream by using promises
                }
            }
        });	//end of request function
    });
}

function makeData(results){    
    var array_articles = [];
    var counter = 0;
    console.log("Getting article content");
    for(var k = 0; k < results.length; k++){
        var contentURL = "https://www.businesslive.co.za" + results[k].link;
        var promise = getArticleContent(contentURL); //Scrape the article data ...
        promise.then(function(article_data){
            array_articles.push(article_data); // receive the JSON data & put into a JSON array
            counter++; 
            if(counter == (results.length)){//When all the requests are done then display it.
                console.log("\nA total of " + counter + " articles have been found.");
     			collection.countDocuments(function(err, count){
                    if(err){
                        console.log("Error with collection " + err);
                    }
                    else{
                        if(count == 0){//If there are no existing collections, insert the new articles
                            console.log("\n Inserting into an empty collection.");
                            insertArticles(array_articles);
                        }
                        else{
                            console.log("\n Cannot insert into an existing collection.");
                        }
                    }
                })
                
            }
        });
    }
}

function sortFunction(a, b){  
    var dateA = new Date(a.article_date).getTime();
    var dateB = new Date(b.article_date).getTime();
    return dateA > dateB ? 1 : -1;  
}; 

function insertArticles(arrArticles){
    var counter = 0;
    for(var i = 0; i < arrArticles.length; i++){
       
        collection.insertOne(arrArticles[i], function(err, article){
            if(err){
                console.log("Error inserting articles " + err);
            }
            else {
                counter++;
                console.log("Article inserted # " + counter + " inserted!");
            }
        });
    }//end of for loop 
	//
    if(counter == arrArticles.length){
        console.log("All articles have been inserted successfully!");
    }
}

app.get("/delete_articles", function(req, res){
    collection.deleteMany();
    res.send("Articles deleted!");
})


app.get("/get_articles", function(req, res){
    var arrArticles = [];
    console.log("Retrieving article data, please wait...");

    var promise = getArticleData();
    promise.then(function(results){
        makeData(results);
        res.send(results);
        res.end();
        console.log("Results sent!");
    });
})//end of API get articles	



