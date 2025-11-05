package com.example.huellapp.DAO


import androidx.room.*
import com.example.huellapp.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuario LIMIT 1")
    fun getUsuario(): Flow<Usuario?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: Usuario)

    @Update
    suspend fun updateUsuario(usuario: Usuario)

    @Query("DELETE FROM usuario")
    suspend fun deleteUsuario()
}
