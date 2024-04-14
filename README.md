# Matrix-codes API

Matrix Codes API is a versatile Reactive REST API for generating and decoding various matrix codes, including barcodes and QR codes.

Utilizing Java 21 and reactive-stack web framework [Spring Webflux](https://spring.io/), build using [Kotlin](https://kotlinlang.org/) in functional way focusing on TDD and leveraging:
- [OpenAPI specification](https://swagger.io/resources/open-api/)
- [ZXing library](https://github.com/zxing/zxing) 
- [Atlassian swagger request validator](https://bitbucket.org/atlassian/swagger-request-validator)
- [API design guidelines](https://opensource.zalando.com/restful-api-guidelines/)

## Service URL

## Features

- Generate QR codes with customizable content.
- Create barcodes for various types of data.
- Simple and intuitive API endpoints for easy integration.

## Branches

- The `main` branch is where the site is automatically built from, and is the place to put changes relevant to the current version.
- The `stage` branch is where we store changes that are related to the next release.
- The `dev` branch is where we store changes that are related to the next release.
- The `feature/` branch used for work on new feature.

## License

[GPL-3.0 license](LICENSE)

## Author Information

[Mislav Matoković](https://github.com/mmatokovic)