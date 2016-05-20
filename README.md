# mpc-hc-api

[MPC-HC](https://mpc-hc.org/), or Media Player Classic - Home Cinema, is an open source media player for Microsoft Windows.

mpc-hc-api is a Java library for controlling MPC-HC over a network.

Download
--------

mpc-hc-api is available on Maven Central. Simply add it as a dependency to your pom.

```xml
<dependency>
  <groupId>com.eeeeeric</groupId>
  <artifactId>mpc-hc-api</artifactId>
  <version>1.0.0</version>
</dependency>
```

Quickstart
----------

First, you'll need to enable the network interface to MPC-HC. 

* Launch MPC-HC
* Press `o` for options
* Navigate to `Player` > `Web Interface`
* Check the box for `Listen on port`
* Select a port

Now in your Java application...

```java
MediaPlayerClassicHomeCinema mpc = new MediaPlayerClassicHomeCinema("127.0.0.1", 55555);

// Browse for files
FileTable root = mpc.browse();
List<FileInfo> files = root.getFiles();

// Realistically, you'll want to display this in your UI, and let the user
// navigate the file tree, and select a file for playback
if (!files.isEmpty())
{
  FileInfo file = files.get(0);
  if (!file.isDirectory())
  {
    mpc.openFile(file);
  }
}

// Control the player
Assert.assertTrue(mpc.isPlaying());
mpc.setMute(true);
mpc.toggleMute();
mpc.setVolume(50);
mpc.pause();
TimeCode fiveMinutes = new TimeCode("00:05:00");
mpc.seek(fiveMinutes);
mpc.play();
```


License
-------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.