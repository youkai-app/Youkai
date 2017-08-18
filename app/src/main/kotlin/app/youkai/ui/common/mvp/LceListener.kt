package app.youkai.ui.common.mvp

class LceListener(val onLoading: () -> Unit, val onContent: () -> Unit, val onError: (e: Throwable) -> Unit)