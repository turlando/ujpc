# Micro Java Parser Combinators

## Requirements

- JDK 11
- Gradle 6.6

## Building

* `gradle jar`: assemble the project into `build/libs/ujpc.jar`
* `gradle test`: run the tests
* `gradle build`: assemble the jar and run the tests
* `gradle javadoc`: generate Javadoc and UML
* `gradle clean`: remove all the generated artifacts

## Interactive environment

Is it possible and encorauged to experiment with this project using a REPL.

If you already have `jshell` in your `PATH` just run
`jshell --class-path build/libs/ujpc.jar`. Otherwise you can run
`gradle --console plain jshell` but your experience will be limited as no text
navigation, history search or command completion will be provided.

## Running the examples

Currently available examples:

* `json`: JSON parser
* `mod`: Amiga tracker module (31 instruments) parser
* `cbse2011`: Loose implementation of the grammar described in
   [this paper][cbse2011]

[cbse2011]: https://spiral.imperial.ac.uk/bitstream/10044/1/33728/2/2011-cbse.pdf

```
java -cp build/libs/ujpc.jar ujpc.example.<example-name>.Main
```

## License

The following license applies to all the files included in this package with
the exclusion of the `src/test/resources/ujpc/example/mod` directory.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
