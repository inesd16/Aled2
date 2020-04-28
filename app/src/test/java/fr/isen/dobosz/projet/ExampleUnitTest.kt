package fr.isen.dobosz.projet

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun passwordAreSimilar(){
        //assertEquals("azertyuiAA1","azertyuiAA1")
        assert(checkPasswords("azertyuiAA1","AzertyuiAA1"))
    }

    fun checkPasswords(passwField : String, confirmPasswField : String): Boolean {
        if (passwField.length == confirmPasswField.length) { //same length
            if (passwField.length < 8) { //length not enough
                System.out.println("Passwords must contain at least 8 characters")

                return false
            } else if (passwField != confirmPasswField) { //different passw
                System.out.println("The passwords must be the same")
                return false
            } else { //password are the same
                var countNumber = false //passw does not contain number
                var countCapLetter = false //passw does not capital letter
                for (letter in passwField) { //Check number in passw
                    try { //is letter a number ?
                        // val i = Integer.parseInt(letter.toString())
                        //System.out.println("C'est un entier")
                        //informations.setText("")
                        countNumber = true
                    } catch (e: Exception) {
                        //System.out.println("Je ne suis pas un entier, et alors ca te derange ?")
                    }
                    if (letter == letter.toUpperCase()) { // letter upCase
                        // Vérifier si le texte est en majuscule
                        countCapLetter = true
                    }
                }
                if (countNumber && countCapLetter)
                    return true
                else {
                    //informations.setText(R.string.stringPasswordInfo)
                    return false
                }
            }
        } else { //password not same length
            System.out.println("The passwords must be the same")
            return false
        }
    }

    @Test
    fun canRegister(){

    }

}
