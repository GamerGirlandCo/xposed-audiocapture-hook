package sh.tablet.recorderhook

import android.util.Log
import de.robv.android.xposed.*
import de.robv.android.xposed.XposedHelpers.findClass
import de.robv.android.xposed.XposedHelpers.findClassIfExists
import de.robv.android.xposed.callbacks.XC_LoadPackage

class ModuleMain : IXposedHookLoadPackage {
    var logtag = "tablet | audio record hook ~ "
    @Override
    override fun handleLoadPackage(lpp: XC_LoadPackage.LoadPackageParam) {
        if(lpp.packageName != "android") {
            return
        }
        XposedBridge.log(logtag + "loading " + lpp.packageName);
        XposedBridge.log(logtag + "proc = " + lpp.processName);
        XposedBridge.log(logtag + findClassIfExists("com.android.server.pm.parsing.pkg.PackageImpl", lpp.classLoader).toString())
        val c = findClass("com.android.server.pm.parsing.pkg.PackageImpl", lpp.classLoader)
        val c2 = findClass("com.android.server.pm.parsing.pkg.AndroidPackage", lpp.classLoader)
        XposedBridge.log("$logtag$c $c2")

        for(item in c.methods) {
            XposedBridge.log(logtag + item.name)
        }
        val mn = "isAllowAudioPlaybackCapture"
        val m = c.getMethod(mn)
        XposedBridge.hookMethod(m, XC_MethodReplacement.returnConstant(10000, true))
//        XposedBridge.hookMethod(c2.getMethod(mn), XC_MethodReplacement.returnConstant(10000, true))
        Log.d(logtag, "done :)")
        XposedBridge.log(logtag + "done :)")
    }
}