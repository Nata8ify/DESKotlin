
import java.io.*
import java.nio.charset.Charset
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

val PASS = "@Kar98Lambda"
val input = Scanner(System.`in`)

fun main(args: Array<String>) {
    if(login()) {
        welcomeArt()
        option()
    }
}

lateinit var pass : String
fun login(): Boolean{

    if(System.console() != null){
        pass = String(System.console().readPassword("> : ") as CharArray)
    } else {
        println("$ : ")
        pass = input.nextLine()
    }
    if(pass == PASS){
       return true
    }
    return false
}

fun welcomeArt(): Unit {
    println("--------------------------------------------------------")
    println("-----------------------N8IFY00005-----------------------")
    println("---------------------secret the file---------------------")
    println("--------------------------------------------------------")
}

fun option(): Unit {
    println("Choose Option... ")
    println("[1] Encrypt \"d.dat\"")
    println("[2] Decrypt \"e.dat\"")
    println("[3] View decrypted \"e.dat\" [\"e.dat\" must be existed][Read Only]")
    println("[4] Create \"e.dat\" from...")
    println("** e.dat is a encrypted file...")
    println("** d.dat is a decrypted file...")
    print("\n\nPlease select... : ")
    when (input.next()) {
        "1" -> {
            opt1();option()
        }
        "2" -> {
            opt2();option()
        }
        "3" -> {
            opt3();option()
        }
        "4" -> {
            opt4();option()
        }
        else -> option()
    }
}

fun opt1() {//Encrypt d.dat to e.dat
    encrypt("d.dat")
}

fun opt2() {//Decrypt e.dat to d.dat
    decrypt()
}


fun opt3(){//Secret Read Only
    var eDatFis = FileInputStream(File("e.dat"))
    desCipher.init(Cipher.DECRYPT_MODE, secretKey)
    var decrypt = desCipher.doFinal(eDatFis.readBytes())
    println("\n---------------*** START Decrypted e ***-----------------\n")
    println(String(decrypt))
    println("\n----------------*** END Decrypted e ***------------------\n")

    eDatFis.close()
}

fun opt4() {//Create  e.dat
    println("\n--------------------------------------------------------")
    println("Option 4 : Create \"e.dat\" from... ")
    println("--------------------------------------------------------")
    print("\n\nEnter a file name (Full name with extension)... : ")
    var fileName = input.next()
    encrypt(fileName)
    println("\n*****Success!... $fileName is encrypted to \"e.dat\"... *****\n")
}

val secretKey = SecretKeyFactory.getInstance("DES").generateSecret(DESKeySpec("kogy9i8u".toByteArray(Charset.forName("UTF-8"))))
val desCipher = Cipher.getInstance("DES")

fun encrypt(fileName: String) {
    var fis = FileInputStream(File(fileName))
    val dos = DataOutputStream(FileOutputStream("e.dat"))
    desCipher.init(Cipher.ENCRYPT_MODE, secretKey)
    var encrypt = desCipher.doFinal(fis.readBytes())
    dos.write(encrypt)
    dos.flush()
    dos.close()
    fis.close()
    File("d.dat").delete()
    println("\n*****Encrypted..*****\n")
}

/////
fun decrypt() {
    var eDatFis = FileInputStream(File("e.dat"))
    val dos = DataOutputStream(FileOutputStream(File("d.dat")))
    desCipher.init(Cipher.DECRYPT_MODE, secretKey)
    var decrypt = desCipher.doFinal(eDatFis.readBytes())
    dos.write(decrypt)
    dos.flush()
    dos.close()
    eDatFis.close()
    File("e.dat").delete()
    println("\n*****Decrypted..*****\n")
}
