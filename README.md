# LokalApp

LokalApp is a cryptocurrency tracking application developed in Java and Kotlin using the Gradle build system. The application fetches real-time data from a cryptocurrency API and displays it in a user-friendly format.

## Key Components

### `MainActivity.java`

This is the main entry point of the application. It handles the API calls and updates the UI accordingly. It also manages a countdown timer that updates the last refresh time.

### `CryptoDataRecyclerViewAdapter.java`

This class is a custom adapter for a RecyclerView. It takes a list of `CryptoData` objects and binds the data to the views in the RecyclerView. It also handles the inflation of the layout for each item in the RecyclerView.

### `CryptoData.java`

This class represents a single cryptocurrency data. It contains information such as the full name of the cryptocurrency, its symbol, maximum supply, icon URL, and exchange rate.

## How to Run

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Run the `MainActivity.java` file.
4. I have also added the lokalApp.apk file install apk directly in your device to run the application

## Dependencies

- Retrofit: Used for making API calls.
- Glide: Used for loading images from URLs.

## Future Improvements

- Add a search functionality to search for specific cryptocurrencies.
- Implement a favorites system where users can save their favorite cryptocurrencies for easy access.
- Add more detailed information for each cryptocurrency.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)
