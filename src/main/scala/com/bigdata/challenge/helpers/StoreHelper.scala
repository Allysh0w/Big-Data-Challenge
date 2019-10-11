package com.bigdata.challenge.helpers

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import com.bigdata.challenge.manager.DatabaseManager
import com.bigdata.challenge.model.Contracts.UserInfo
import doobie.implicits._
import doobie.util.update.Update0

trait StoreHelper extends DatabaseManager {

  private def insertUser(userInfo: UserInfo) =
    sql"insert into user_access (user_name) values (${userInfo.userName}) on conflict do nothing".update

  private def insertUrlViewed(userInfo: UserInfo): Update0 =
    sql"INSERT INTO url_access (link) values (${userInfo.url}) on conflict do nothing".update

  private def selectUserId(user: UserInfo) = {
    sql"select id from user_access WHERE user_name = ${user.userName}"
      .query[Int]
      .to[List]
      .transact(transactorManager)
      .unsafeRunSync
      .take(1)
  }

  protected def selectUrlId(user: UserInfo) = {
    sql"select id from url_access WHERE link = ${user.url}"
      .query[Int]
      .to[List]
      .transact(transactorManager)
      .unsafeRunSync
      .take(1)
  }


  private def insertRelationUrlViewed(idUser: Int, idUrl:Int): Update0 =
    sql"INSERT INTO url_relation (id_user, id_url, rating) values ($idUser,$idUrl,1.0) on conflict do nothing".update

  protected def persistData(userInfo: UserInfo) = {

    insertUser(userInfo).run.transact(transactorManager).unsafeRunSync
    val userId = selectUserId(userInfo)
    insertUrlViewed(userInfo).run.transact(transactorManager).unsafeRunSync
    val urlId = selectUrlId(userInfo)
    insertRelationUrlViewed(userId.head, urlId.head).run.transact(transactorManager).unsafeRunSync
    HttpResponse(StatusCodes.OK)

  }

  protected def deleteAllInfoFromDB(): Unit ={
    sql"TRUNCATE url_relation".update.run.transact(transactorManager).unsafeRunSync()
    sql"TRUNCATE user_access CASCADE".update.run.transact(transactorManager).unsafeRunSync()
    sql"TRUNCATE url_access CASCADE".update.run.transact(transactorManager).unsafeRunSync()
  }


}
