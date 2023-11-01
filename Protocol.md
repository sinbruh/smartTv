# Protocol

Messages are separated by newline.

The client always sends a request first, the server responds.
There can be several requests/responses during one connection

The following request could be sent from client to server:
* "1" turn on the TV
* "0" turn off the TV
* "c" get the number of channels
* "s###" set current channel, where the ### is the desired channel number as a string, can be one or several bytes,
   up to the next newline ("s1", "s25", ...)
* "g" get the current channel.

Server can respond with:
* "o" - performed the necessary action
* "c###" - report the number of channels, where ### is the number of channels as a string. Can be several bytes,
   until the next new line.
* "eM" - if the operation failed, where M is an error message - string until the new line. For example, if the
   error message is "Invalid channel number", the response will be "eInvalid channel number"
* "a###" reports the current "active Channel", where ### is the number of the current active channel.