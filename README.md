# Matrix-codes API

Matrix Codes API is a versatile Reactive REST API for generating and decoding various matrix codes, including barcodes and QR codes.

Built using Java 21 and the [Spring 3.2.4](https://spring.io/) using [Kotlin lang](https://kotlinlang.org/) TDD and leveraging:
- [ZXing library](https://github.com/zxing/zxing) 
- [OpenAPI specification](https://swagger.io/resources/open-api/)
- [atlassian/swagger-request-validator-core](https://bitbucket.org/atlassian/swagger-request-validator)
- [API design guidelines](https://opensource.zalando.com/restful-api-guidelines/)

## Features

- Generate QR codes with customizable content.
- Create barcodes for various types of data.
- Decode matrix codes from images or scanned documents.
- Simple and intuitive API endpoints for easy integration.

## Branches

Versatile API for generating and decoding various matrix codes, including barcodes and QR codes

- The `main` branch is where the site is automatically built from, and is the place to put changes relevant to the current version.
- The `stage` branch is where we store changes that are related to the next release.
- The `dev` branch is where we store changes that are related to the next release.
- The `feature/` branch used for work on new feature.

## License

[GPL-3.0 license](LICENSE)

## Author Information

[Mislav Matoković](https://github.com/mmatokovic)