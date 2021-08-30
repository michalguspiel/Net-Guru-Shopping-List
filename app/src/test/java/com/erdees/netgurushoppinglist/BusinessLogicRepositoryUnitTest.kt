package com.erdees.netgurushoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.erdees.netgurushoppinglist.model.models.ShoppingList
import com.erdees.netgurushoppinglist.model.repositories.BusinessLogicRepository
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*


@RunWith(JUnit4::class)
class BusinessLogicRepositoryUnitTest {

    private lateinit var businessLogicRepository: BusinessLogicRepository

    private lateinit var testShoppingList : ShoppingList
    private lateinit var secondTestShoppingList : ShoppingList

    @Before
    fun setup(){
        businessLogicRepository = BusinessLogicRepository()
        testShoppingList =  ShoppingList(1,"TEST LIST",true, Calendar.getInstance().time )
        secondTestShoppingList =  ShoppingList(2,"TEST LIST 2",true, Calendar.getInstance().time )
    }

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `Given test shopping list is set as a presented shopping list while getting shoppin`(){
        businessLogicRepository.setShoppingListToPresent(testShoppingList)
        assertEquals(testShoppingList,businessLogicRepository.getShoppingListToPresent().value)
    }

    @Test
    fun `Given I set shopping list to present and then cleared presented Shopping list, getShoppingListToPresent should return null`(){
        businessLogicRepository.setShoppingListToPresent(testShoppingList)
        businessLogicRepository.clearShoppingListToPresent()
        assertEquals(null,businessLogicRepository.getShoppingListToPresent().value)
    }

    @Test
    fun `Given i set test shopping list and then second test shopping list getShoppingListToPresent should not be testShoppingList, but secondTestShoppingList`(){
        businessLogicRepository.setShoppingListToPresent(testShoppingList)
        assertEquals(testShoppingList,businessLogicRepository.getShoppingListToPresent().value)
        businessLogicRepository.setShoppingListToPresent(secondTestShoppingList)
        assertNotEquals(testShoppingList,businessLogicRepository.getShoppingListToPresent().value)
        assertEquals(secondTestShoppingList,businessLogicRepository.getShoppingListToPresent().value)
    }


}