# Wonder Words

Wonder Words is an android application built with kotlin programming language that uses the Quotes API to bring you all kinds of quotes to your fingertips. This app allows users to authenticate, fetch quotes by category, search quotes, manage user profiles, and much more. The app is built using clean architecture principles to ensure a scalable and testable codebase.

## Screenshots

![111-imageonline co-merged](https://github.com/EngFred/Movie-Mania-Browser/assets/136785545/5eb5d3f6-a947-4bd8-86e8-2843ab0e7835)

## Features

**User Authentication**: Secure user authentication to manage access.
- **Fetch Quotes**: Retrieve a wide range of quotes.
- **Fetch Quotes by Category**: Filter quotes based on categories.
- **User Profile**: Manage user profiles.
- **Change App Theme**: Switch between light and dark themes.
- **Quote of the Day**: Get the daily inspirational quote.
- **Pagination and caching**: Efficiently load quotes with pagination that can still be accessed even when offline.
- **Quote Reactions**: Upvote, downvote, add to favorites, and remove from favorites.
- **Share Quotes**: Share your favorite quotes with other apps.
- - **Create Quote**: Create a new quote.
- **Fetch Quote Details**: Get detailed information about a specific quote.

## Project Structure

The project is organized by feature, making it easy to scale and maintain.

## Packages Used

### Key Packages:

- **Retrofit**
  - For making network calls to the Quotes API

- **DataStore**
  - For saving and retrieving user tokens and the app theme.

- **Jetpack compose**
  - For creating beatiful user interface and a good user experience

To run this project locally, follow these steps:

1. **Clone the repository:**
    ```sh
    git clone https://github.com/engFred/wonder-words-kotlin.git
    cd wonder-words
    ```

2. **Install dependencies:**
    ```sh
    sync the gardle file to download all the required depedencies
    ```


3. **Configure Quotes API Key:**
    - Obtain an API key from [QuotesApi](https://favqs.com/api/).
    - Add the API key to your project (e.g., in a `.env` file or directly in the code).

4. **Build and Run:**
    - Run the app on an emulator or a physical device:
      ```sh
      flutter run
      ```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or feedback, please reach out to:

- Engineer Fred - [engfred88@gmail.com](mailto:engfred88@gmail.com)
- Project Link: [https://github.com/engFred/wonder-words-flutter](https://github.com/engFred/wonder_words_kotlin)

## Acknowledgements

Special thanks to the android developer community that work hard every day to improve coding quality and the FavQs Api developers for creating such an adventurous api.
