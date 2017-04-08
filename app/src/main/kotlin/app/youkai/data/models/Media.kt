package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.Type

/**
  * This model is needed because jasminb.jsonapi cannot parse kotlin's open classes as objects in models that are also open classes.
  * See https://github.com/jasminb/jsonapi-converter/issues/110 for more info.
  */
@Type("media")
class Media : BaseMedia("media")