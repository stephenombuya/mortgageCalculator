# Mortgage Calculator

## Overview
This Java-based Mortgage Calculator is a comprehensive application designed to help users calculate mortgage payments, compare loan scenarios, and understand the financial implications of home financing.

## Features
- Calculate monthly mortgage payments
- Support for fixed-rate and adjustable-rate mortgages
- Detailed amortization schedule generation
- Loan comparison functionality
- Interactive command-line interface
- Flexible input handling for various loan scenarios

## Prerequisites
- Java Development Kit (JDK) 11 or higher
- Maven (for dependency management and building)

## Installation

### Cloning the Repository
```bash
git clone https://github.com/stephenombuya/mortgageCalculator/tree/main
cd mortgage-calculator
```

### Building the Project
```bash
mvn clean install
```

## Usage

### Running the Application
```bash
java -jar target/mortgage-calculator.jar
```

### Command-Line Options
- `-p, --principal`: Loan principal amount
- `-r, --rate`: Annual interest rate
- `-t, --term`: Loan term in years
- `-c, --compare`: Enable loan comparison mode

### Example Commands
```bash
java -jar mortgage-calculator.jar -p 250000 -r 4.5 -t 30
java -jar mortgage-calculator.jar --compare
```

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/mortgagecalculator/
│   │       ├── MortgageCalculator.java
│   │       ├── LoanProcessor.java
│   │       └── AmortizationSchedule.java
│   └── resources/
└── test/
    └── java/
        └── com/mortgagecalculator/
            ├── MortgageCalculatorTest.java
            └── LoanProcessorTest.java
```

## Testing
Run unit tests using Maven:
```bash
mvn test
```

## Dependencies
- Apache Commons Math: Advanced mathematical calculations
- Jackson: JSON processing
- JUnit: Unit testing framework

## Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License
Distributed under the MIT License. See `LICENSE.md` for more information.
