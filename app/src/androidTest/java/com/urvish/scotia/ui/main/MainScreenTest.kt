package com.urvish.scotia.ui.main

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.urvish.dataprovider.data.network.model.GitHubUser
import com.urvish.dataprovider.data.network.model.GitHubUserRepo
import com.urvish.scotia.ui.common.theme.ScotiaAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.urvish.scotia.ui.common.R as CommonR

class MainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    @Before
    fun setUp() {

    }

    @Test
    fun test_user_search_with_null() {
        composeTestRule.setContent {
            ScotiaAppTheme {
                GitUserSearchView(onSearch = {})
            }
        }
        with(composeTestRule) {
            onNodeWithTag("SearchButton")
                .assertExists()
                .assertHasClickAction()
                .performClick()
            onNodeWithText(getString(id = CommonR.string.error_user_no_input))
                .assertIsDisplayed()
        }
    }

    @Test
    fun text_user_with_success() {
        composeTestRule.setContent {
            ScotiaAppTheme {
                GitHubUserContent(userUIState = GitHubUserUIState.Success(GitHubUser(name = "Urvish")))
            }
        }
        with(composeTestRule) {
            onNodeWithText("Urvish").assertExists()
            onNodeWithTag("UserImage").assertExists()
        }
    }

    @Test
    fun text_user_with_user_repo_success() {
        composeTestRule.setContent {
            ScotiaAppTheme {
                GitHubUserRepoContent(
                    repoUIState = UserRepoUIState.Success(
                        arrayListOf(
                            GitHubUserRepo(
                                name = "Urvish",
                                id = 100,
                            ),
                            GitHubUserRepo(
                                name = "Scotia",
                                id = 101,
                            )
                        )
                    )
                )
            }
        }
        with(composeTestRule) {
            onNodeWithText("Scotia").assertExists()
            onNodeWithText("RBC").assertDoesNotExist()
            onNodeWithText("Urvish").assertExists()
        }
    }


    private fun getString(@StringRes id: Int) = composeTestRule.activity.getString(id)
}