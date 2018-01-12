import java.sql.*
import java.util.*
import javax.xml.stream.events.Characters

class D{

    lateinit var name : String
    lateinit var password : String
    lateinit var key : String

    val derbyConn : Connection by lazy {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver")
        DriverManager.getConnection("jdbc:derby:inf;create=true")
    }

    val input = Scanner(System.`in`)
    constructor(){
        println("Initialing...")
        try{
            queryInf()
        } catch (sqlexcp : SQLException){
            initialInfTableAndRecord()
            println("Enter your name (One-time enter / Empty is not allowed) : ")
            var iName = input.nextLine()

            println("Enter your password (Use for accessing any time) : ")
            var iPass = input.nextLine()
            insertNameAndPass(iName, iPass)
        }
    }

    fun queryInf(){
        var sql = "select * from inf"
        var pstm : PreparedStatement = derbyConn.prepareStatement(sql)
        val rs : ResultSet = pstm.executeQuery()
       if(rs.next()){
           name = rs.getString(1)
           password = rs.getString(2)
           key = rs.getString(3)
       } else {
           throw SQLException("BLANK Record")
       }
    }

    fun initialInfTableAndRecord(): Unit{
        var sql: String = "create table inf(name varchar(40), pass varchar(40), ekey varchar(40))"
        var pstm : PreparedStatement = derbyConn.prepareStatement(sql)
        pstm.executeUpdate()
    }

    fun insertNameAndPass(name: String, pass :String): Unit{
        this.name  = name
        this.password  = pass
        this.key  = getGenKey(name)
        var sql: String = "insert into inf values (?, ?, ?)"
        var pstm : PreparedStatement = derbyConn.prepareStatement(sql)
        pstm.setString(1, name)
        pstm.setString(2, pass)
        pstm.setString(3, key)
        println("Inserting New User is Executed! (CODE : ${pstm.executeUpdate()})")

    }

    fun updatePass(pass :String): Unit{
        var sql: String = "update inf set pass = ? where name = ?"
        var pstm : PreparedStatement = derbyConn.prepareStatement(sql)
        pstm.setString(1, pass)
        pstm.setString(2, this.name)
        println("Password Update is Executed! (CODE : ${pstm.executeUpdate()})")
    }

    fun getGenKey(iName: String): String{
        if(iName.length >= 8){return iName}
        var genKey : String = iName
        val random = Random()
        for(i in iName.length..8){
            genKey += random.nextInt(90 - 65).toString()
        }
        return genKey
    }

}