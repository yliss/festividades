# Sample javaee war maven project

This project is used as a startup/template project for javaee war based projects.

Off the shelf, it provides:
- javaee 7 compliant maven project
- REST based example service
- project versioning using [jgitver](https://github.com/jgitver/jgitver-maven-plugin)
- arquillian tests with chameleon container
- default integration-test using wildfly 10.0.0.CR4
- remote integration test using maven profile `remote`

## Build

__Integration tests on embedded server__

- `mvn clean install`

__Integration tests on remote server__

- `mvn -Premote clean install`

## Release

Once your are satisfied of the HEAD commit (ie you performed all your tests)

- `git tag -a -m "release X.Y.Z, additionnal reason" X.Y.Z``
- `mvn -Prelease -DskipTests deploy`
- `git push --follow-tags origin master`