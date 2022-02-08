# PostNote

Post Note is an android note taking application with online editing function.


There are 4 main functions in this application, which are:
1. note-taking
2. getting request content
3. drawing board.

<h3>Note-taking: (GSON, sharedPreferences)</h3> 
This is the basic function of this application, users can create new notes, edit it and get notes saved into the local memory.

<h3>Get request: (okhttp library)</h3> 
After the user enters the edit note activity, the user can edit text content normally or click on the floating button with a ‘+’ symbol to insert content from the url. Then a dialog box will pop up asking the user to insert a url link. After clicking the get button, content from the url link will append at the end of the edit text.

Since getting requests is different from getting HTTP content from the internet, in order to get requests from the internet, okHttp is used as an HTTP client to get data.


<h3>Multi-User Edit: (Express, Node.js, MongoDB, GCP, okhttp)</h3> 
Users can click the connection button in the main activity to create a room to edit notes online. Users need to insert an id, if the id already exists, the user will be connected to the room, else a new room will be created. Users in the room can edit the same note together.

This function is achieved by using an express server, there are two get request routes in the express server for getting and sending note data. When the get data route is requested, the server will check if the id from the request exists in the MongoDB database and return the content of that document, or create a new document with the same id. When the send data request is requested, the server will find the document with the same id and update the content of that document.

<h3>Drawing board: (CustomView)</h3> 
This function is like a memo for users to jot down their ideas. Users can draw on it by hand, and change the color as they want.

This function is created using customView. Each stroke from the user will be saved to an arraylist in onTouch, then every time the onDraw method is called, those strokes will be redrawn on the canvas.
