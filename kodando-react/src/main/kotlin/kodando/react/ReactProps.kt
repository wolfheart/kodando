@file:Suppress("UnsafeCastFromDynamic")

package kodando.react

import kotlin.js.*

interface ReactProps : Json {
    var key: String?
    var ref: Any?
    var children: ReactChildren?
}


var ReactProps.refByName: String?
    get() = ref.unsafeCast<String?>()
    set(value) {
        ref = value
    }

var ReactProps.refBy: ((Any?) -> Unit)?
    get() = ref.unsafeCast<((Any?) -> Unit)?>()
    set(value) {
        ref = value
    }


@JsName("appendElement")
fun ReactProps.append(child: ReactElement?) {
    children = children?.concat(child) ?: reactChildren(child)
}

@JsName("appendString")
fun ReactProps.append(child: String?) {
    children = children?.concat(raw(child)) ?: reactChildren(raw(child))
}

@JsName("appendChildren")
fun ReactProps.append(child: ReactChildren?) {
    children = children?.concat(raw(child)) ?: reactChildren(raw(child))
}


@JsName("spreadTo")
fun ReactProps.spreadTo(other: ReactProps, except: Array<String> = arrayOf()) {
    val json = this
    val otherJson = other

    js("""
    var keys = Object.getOwnPropertyNames(json);

    for (var i = 0; i < keys.length; i++) {
        var key = keys[i];

        if (except.indexOf(key) >= 0 || json[key] === undefined)
            continue;

        otherJson[key] = json[key];
    }
    """)
}