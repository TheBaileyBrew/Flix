# Popular Movies

## API KEY Details

Please add your The Movie Database (TMDB) API KEY to Build.gradle <code>System.getenv('API_KEY) ?: "\"[YOUR KEY HERE]\"")</code>
so that the app can fetch movie data from the API


## Application Details

This app pulls data from the API in three AsyncTasks

**Step One:** Collect all popular film data - populate this data in a Grid RecyclerView.

**Step Two:** Collect additional film details (Tagline, Runtime & Genres) - populate this data to the Detail Activity upon layout creation.

**Step Three:** Collect film credits - populate this data in a Horizontal RecyclerView upon layout creation.

## Application Preferences

This app allows users to search the API for **Top Rated** or **Currently Popular** films via SharedPrefs.

This all also allows users to filter the API result of above return by Original Language (English, Chinese, Korean, Indian, Japanese or All)

