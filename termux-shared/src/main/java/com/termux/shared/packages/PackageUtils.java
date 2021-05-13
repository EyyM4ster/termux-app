package com.termux.shared.packages;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.termux.shared.data.DataUtils;
import com.termux.shared.logger.Logger;

import java.security.MessageDigest;

public class PackageUtils {

    /**
     * Get the {@link Context} for the package name.
     *
     * @param context The {@link Context} to use to get the {@link Context} of the {@code packageName}.
     * @return Returns the {@link Context}. This will {@code null} if an exception is raised.
     */
    public static Context getContextForPackage(@NonNull final Context context, String packageName) {
        try {
            return context.createPackageContext(packageName, Context.CONTEXT_RESTRICTED);
        } catch (Exception e) {
            Logger.logStackTraceWithMessage("Failed to get \"" + packageName + "\" package context.", e);
            return null;
        }
    }

    /**
     * Get the {@link PackageInfo} for the package associated with the {@code context}.
     *
     * @param context The {@link Context} for the package.
     * @return Returns the {@link PackageInfo}. This will be {@code null} if an exception is raised.
     */
    public static PackageInfo getPackageInfoForPackage(@NonNull final Context context) {
            return getPackageInfoForPackage(context, 0);
    }

    /**
     * Get the {@link PackageInfo} for the package associated with the {@code context}.
     *
     * @param context The {@link Context} for the package.
     * @param flags The flags to pass to {@link PackageManager#getPackageInfo(String, int)}.
     * @return Returns the {@link PackageInfo}. This will be {@code null} if an exception is raised.
     */
    public static PackageInfo getPackageInfoForPackage(@NonNull final Context context, final int flags) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), flags);
        } catch (final Exception e) {
            return null;
        }
    }

    /**
     * Get the app name for the package associated with the {@code context}.
     *
     * @param context The {@link Context} for the package.
     * @return Returns the {@code android:name} attribute.
     */
    public static String getAppNameForPackage(@NonNull final Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    /**
     * Get the package name for the package associated with the {@code context}.
     *
     * @param context The {@link Context} for the package.
     * @return Returns the package name.
     */
    public static String getPackageNameForPackage(@NonNull final Context context) {
        return context.getApplicationInfo().packageName;
    }

    /**
     * Get the {@code targetSdkVersion} for the package associated with the {@code context}.
     *
     * @param context The {@link Context} for the package.
     * @return Returns the {@code targetSdkVersion}.
     */
    public static int getTargetSDKForPackage(@NonNull final Context context) {
        return context.getApplicationInfo().targetSdkVersion;
    }

    /**
     * Get the {@code versionName} for the package associated with the {@code context}.
     *
     * @param context The {@link Context} for the package.
     * @return Returns the {@code versionName}. This will be {@code null} if an exception is raised.
     */
    public static Boolean isAppForPackageADebugBuild(@NonNull final Context context) {
        return ( 0 != ( context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE ) );
    }

    /**
     * Get the {@code versionCode} for the package associated with the {@code context}.
     *
     * @param context The {@link Context} for the package.
     * @return Returns the {@code versionCode}. This will be {@code null} if an exception is raised.
     */
    public static Integer getVersionCodeForPackage(@NonNull final Context context) {
        try {
            return getPackageInfoForPackage(context).versionCode;
        } catch (final Exception e) {
            return null;
        }
    }

    /**
     * Get the {@code versionName} for the package associated with the {@code context}.
     *
     * @param context The {@link Context} for the package.
     * @return Returns the {@code versionName}. This will be {@code null} if an exception is raised.
     */
    public static String getVersionNameForPackage(@NonNull final Context context) {
        try {
            return getPackageInfoForPackage(context).versionName;
        } catch (final Exception e) {
            return null;
        }
    }


    /**
     * Get the {@code SHA-256 digest} of signing certificate for the package associated with the {@code context}.
     *
     * @param context The {@link Context} for the package.
     * @return Returns the{@code SHA-256 digest}. This will be {@code null} if an exception is raised.
     */
    public static String getSigningCertificateSHA256DigestForPackage(@NonNull final Context context) {
        try {
            PackageInfo packageInfo = getPackageInfoForPackage(context, PackageManager.GET_SIGNATURES);
            if (packageInfo == null) return null;
            return DataUtils.bytesToHex(MessageDigest.getInstance("SHA-256").digest(packageInfo.signatures[0].toByteArray()));
        } catch (final Exception e) {
            return null;
        }
    }

}
