This is simple Java app which test the speed of the following cases:
* Single string text
* Simple protocol buf object
* Large protocol buf list of object
* Large JSON list of object

The sequence as following:
1. Read the file for sample, if not exist then generate the sample data
2. Read the sample data
3. Convert to object
4. Print the object
5. Complete the test

Background:
During short discussion with friends during the morning coffee session. They told me that
Stock Broker system is very fast and they use single string to present the command and they use single string which means very little amount of data will be transferred over the wire and very small data and memory to convert as a result of that the speed the system will be extremely quick. A few year ago, Google introduce Protocol Buffers for serializing structure data and it is built for speed. Thus, I want to run a test to compare time to read all the data from file until all the data has been printed out.
 
Here are the results:
* Total time to parse string from input stream to print it out, will take approx 18 milliseconds
* Protocol Buffers takes 35 milliseconds to complete
* Time to read and parse 10,000 objects for Protocol Buffers will take 28 milliseconds
* Jackson JSON takes 302 milliseconds to read and parse 10,000 objects

It is very interesting result for me. I was surprised by the fact that the time to handle 10,000 objects takes less time for single one. However, my friend was right, short simple string command is very quick to handle, rather than structured object even with lighting fast serializer.



Feel free to contribute if you wish. You are welcome.