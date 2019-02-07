/*
 * This software is licensed under the Apache 2 license, quoted below.
 *
 * Copyright 2019 Astraea, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     [http://www.apache.org/licenses/LICENSE-2.0]
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 */

package astraea.spark.rasterframes.model
import astraea.spark.rasterframes.encoders.CatalystSerializer
import geotrellis.proj4.CRS
import geotrellis.vector.Extent
import org.apache.spark.sql.types.{StructField, StructType}

case class TileContext(crs: CRS, extent: Extent)
object TileContext {
  implicit val serializer: CatalystSerializer[TileContext] = new CatalystSerializer[TileContext] {
    override def schema: StructType = StructType(Seq(
      StructField("crs", CatalystSerializer[CRS].schema, false),
      StructField("extent", CatalystSerializer[Extent].schema, false)
    ))
    override protected def to[R](t: TileContext, io: CatalystSerializer.CatalystIO[R]): R = io.create(
      io.to(t.crs),
      io.to(t.extent)
    )
    override protected def from[R](t: R, io: CatalystSerializer.CatalystIO[R]): TileContext = TileContext(
      io.get[CRS](t, 0),
      io.get[Extent](t, 1)
    )
  }
}
