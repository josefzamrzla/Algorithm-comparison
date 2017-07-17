# Algorithm comparison

Just for fun - one of my university projects - a visualization that compares (or at least tries to compare) four sorting algorithms. Written in Java (in 2008).

## Compile and run

### 1. compile
```bash
javac -d ./build *.java
```

### 2. build
```bash
cd build
jar cfm algorithmcomparison.jar manifest.txt algorithmcomparison/*.class
```

### 3. run

Run in *build* folder:
```bash
java -jar algorithmcomparison.jar
```

## Just try precompiled version

Run in root folder:
```bash
java -jar algorithmcomparison.jar
```

Tested with Java(TM) SE Runtime Environment (build 1.8.0_121-b13)
