<h1 align="center">Welcome to Java-Native-NCurses 👋</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
</p>

> Java NativeCurses is an NCurses wrapper written in Java, it provides direct access to many NCurses methods, and also provides an API for directly creating GUIs using Windows Buttons and Text fields

***
## Usage

### Running the Application
Building the library:
- compiling and linking the library
```sh
cd ./src/native
make compile
make lib
```
- Generating the header and then compiling and linking the library
```sh
cd ./src/native
make
```

Running the Jar
```sh
java -Djava.library.path="src/native" -jar "build/libs/NativeTUI-1.0.0-all.jar"
```

### Creating a GUI in Java NativeCurses
```java
...
public static void main(String[] args) {
    int fps = 30;//too high fps will cause flickering
    WindowManager windowManager = new WindowManager(fps);
    Window window = windowManager.addWindow(0, new MyWindow(), true);//add a window to the screen and make it be the actively rendered one
    
    windowManager.render();//starts the drawing
    windowManager.handleKey();//starts listening for inputs
}
```
```java
public class MyWindow {
    public MyWindow() {
        super(null,
                NativeCurses.instance().getWidth() / 2 - 30, //x position
                NativeCurses.instance().getHeight() / 2 - 6, //y position
                60, //width in characters
                12, //height in lines
                ColorBuilder //create a new color pair
                        .create()
                        .defineForeground(new Color(70, 200, 150)) //Java Colors and Hex are supported
                        .defineBackground(1) //default black -> NativeCurses.BLACK
                        .build(), //get the color pair id
                "MyWindow"); //title
    }
    
    public void init() {
        //initialize your components like Textfields
    }
    
    public void draw() {
        //Draw things like Text which aren't a component
    }
    
    public boolean handleKey(char ch) {
        boolean buttons = this.handleButtonKeys(ch);
        if(buttons) return true;//returns true if a key has been handled
        
        return this.handleKeysForSub(someComponent, ch);//returns if a key has been handled by a component
        
    }
}
```
A working example of a simple login and chat interface can be found in [ExampleProject](src/main/java/de/johannes/Main.java)

## Author

**Johannes Hans** ([@CommandJoo](https://github.com/CommandJoo))

## Show your support

Give a ⭐️ if this project helped you!
