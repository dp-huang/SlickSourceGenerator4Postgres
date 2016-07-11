## SlickSourceGenerator4Postgres

The default source generator in Slick always generates all the schemas for postgresql, which is not good in the case that just some specific schemas are needed.
This small project extends the default generator and accepts a schema list as the filter.
