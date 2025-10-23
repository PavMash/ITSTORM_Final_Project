package com.itstorm.core_data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import com.itstorm.core_data.db.entities.UserSessionCrossRef

@Dao
interface UserSessionCrossRefDao {

    @Insert(onConflict = IGNORE)
    suspend fun insertCrossRef(crossRef: UserSessionCrossRef)

    @Insert(onConflict = IGNORE)
    suspend fun insertAllCrossRefs(crossRefs: List<UserSessionCrossRef>)

    @Delete
    suspend fun deleteCrossRef(crossRef: UserSessionCrossRef)
}