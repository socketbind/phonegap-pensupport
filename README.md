phonegap-pensupport
===================

(Android 3.2+ only)

Support for receiving pen events on specific DOM elements. I wouldn't say this is the nicest approach to do it but it definitely works on the Galaxy Note. Improvements to the code are very much welcome.

Known limitations
=================

You need to include the following meta tag, in order to make it work:

    <meta name="viewport" content="width=device-width, target-densitydpi=device-dpi">

I might get it working with a target dpi setting different than device-dpi someday, but in the meantime this is definitely needed to make it work properly.

Usage
=====

Include js/pensupport.js in your page then use the PenSupport object similar to this:

    document.addEventListener('deviceready', function() {
        var penArea = document.getElementById("penArea");
        var penCanvasContext = penArea.getContext('2d');

        var penSupport = new PenSupport();
        penSupport.enableEvents(penArea);

        penArea.addEventListener("pen", function(evt) {
            penCanvasContext.fillRect(evt.x, evt.y, 2, 2);
        });
    }, false);

Calling penSupport.enableEvents(<reference to a dom element>) will cause the plugin to send pen related events to that element. These events will have the x, y and action field defined, the first two being the coordinates relative to the top left corner of the element, the third one is the state the stylus was in, which can be one of third values: "ACTION_DOWN", "ACTION_UP" or "ACTION_MOVE".

If you want to stop receiving these events, you can call penSupport.disableEvents(). There is no support yet for receiving pen events on multiple DOM elements.

License
=======

The MIT License

Copyright (c) 2012 Simon MacDonald

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
