package huji.postpc.ahmadok.todoitemlist

import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TodoItemsHolderImplTest : TestCase() {
    @Test
    fun when_addingTodoItem_then_callingListShouldHaveThisItem() {
        // setup
        val holderUnderTest = TodoItemsHolderImpl()
        assertEquals(0, holderUnderTest.currentItems.size)

        // test
        holderUnderTest.addNewInProgressItem("do shopping")

        // verify
        assertEquals(1, holderUnderTest.currentItems.size)
    } // TODO: add at least 10 more tests to verify correct behavior of your implementation of `TodoItemsHolderImpl` class
    /**
     * Tests to add:
     * 1 - when adding item then item has item
     * 2 - when marking/unmarking item as done then should be done/not
     * 3 - marking and unmarking item should make it unmarked
     * 4-
     */
}