//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.preferencesDataStore
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.core.stringPreferencesKey
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
//
//class UserPreferences(private val context: Context) {
//
//    private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
//    private val USER_EMAIL = stringPreferencesKey("user_email") // optional
//
//    /** Save login state and optional email */
//    suspend fun saveLoginState(isLoggedIn: Boolean, email: String? = null) {
//        context.dataStore.edit { prefs ->
//            prefs[IS_LOGGED_IN] = isLoggedIn
//            email?.let { prefs[USER_EMAIL] = it }
//        }
//    }
//
//    /** Flow for observing login state */
//    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data
//        .map { prefs -> prefs[IS_LOGGED_IN] ?: false }
//
//    /** Optional: Flow for observing user email */
//    val userEmailFlow: Flow<String?> = context.dataStore.data
//        .map { prefs -> prefs[USER_EMAIL] }
//
//    /** Clear login info for logout */
//    suspend fun logout() {
//        context.dataStore.edit { prefs ->
//            prefs[IS_LOGGED_IN] = false
//            prefs.remove(USER_EMAIL)
//        }
//    }
//}
//
