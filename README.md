<h1 align="center">Welcome to Tuirmite 👋</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.5-blue.svg?cacheSeconds=2592000" />
</p>
<a href="https://www.cprogramming.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/c/c-original.svg" alt="c" width="40" height="40"/> </a> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a>

> Tuirmite is an NCurses wrapper written in Java, it provides direct access to many NCurses methods, and also provides an API for directly creating GUIs using Windows Buttons and Text fields

***
## Quickstart
Download
[The latest version](https://github.com/CommandJoo/Tuirmite/releases/latest)
and add it as a Library to your Project

Alternatively
Clone the Repository
```shell
git clone https://github.com/CommandJoo/Java-Native-NCurses
```

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
java -jar "build/libs/Tuirmite.jar"
#or
./run
```

### Creating a GUI in Tuirmite
```java
...
public static void main(String[] args) {
    int fps = 30;//too high fps will cause flickering
    int width = 40;//minimum width in characters
    int width = 10;//minimum height in lines
    WindowManager windowManager = new WindowManager(fps, width, height, false);
    MyWindow win = WindowBuilder<MyWindow>()
            .at(0,0).bounds(100, 20)
            .color(CursesConstants.DARK_CYAN)
            .build(MyWindow::new);
    Window window = windowManager.addWindow(0, win);//add a window to the screen and make it be the actively rendered one
    
    windowManager.render();//starts the drawing
    windowManager.handleKey();//starts listening for inputs
}
```
```java
public class MyWindow {
    public MyWindow() {}
    
    public void init() {
        //initialize your components like Textfields
    }
    
    public void draw() {
        //Draw things like Text which aren't a component
    }
    
    public boolean handleKey(char ch) {
        for(Component comp : getComponents()) {
            comp.handleKey(ch);
        }
        return false;
    }
    
    public boolean handleClick(Mouse mouse) {
        return false;
    }
}
```
A working example of all the basic components and formatting can be found in [ExampleProject](src/main/java/de/johannes/example/Example.java)
<br> A Simple Snake-Game can also be found here: [Snake](src/main/java/de/johannes/snake/SnakeWindow.java)
***
## Author

**Johannes Hans** ([@CommandJoo](https://github.com/CommandJoo))

## Show your support

Give a ⭐️ if this project helped you!
