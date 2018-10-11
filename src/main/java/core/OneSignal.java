package core;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class OneSignal
{
    private String apiKey;
    private String app_id;
    private String url;
    private List<String> include_player_ids, included_segments, excluded_segments;
    private Map<String, String> headings, contents;
    private Map<String, String> data;
    /**
     * Buttons to add to the notification.
     * <p>
     * iOS 8.0+, ANDROID 4.1+
     */
    private List<Button> buttons;
    /**
     * Only one notification with the same id will be shown on the device. Use the same id to update an existing
     * notification instead of showing a new one.
     * <p>
     * <i>This is known as {@code apns-collapse-id} on iOS and {@code collapse_key} on Android.</i>
     * <p>
     * iOS 10+, ANDROID
     */
    private String collapse_id;
    /**
     * Time To Live - In seconds. The notification will be expired if the device does not come back online within this
     * time. The default is 259,200 seconds (3 days).
     * <p>
     * iOS, ANDROID, CHROME, CHROMEWEB
     */
    private Integer ttl;
    /**
     * Delivery priority through the push server (example GCM/FCM). Pass {@code 10} for high priority. Defaults to
     * normal priority for Android and high for iOS. For Android 6.0+ devices setting priority to high will wake the
     * device out of doze mode.
     * <p>
     * iOS, ANDROID, CHROME, CHROMEWEB
     */
    private Integer priority;
    /**
     * Indicates whether to send to all devices registered under your app's Apple iOS platform.
     * <p>
     * iOS
     */
    private Boolean isIos;
    /**
     * Indicates whether to send to all devices registered under your app's Google Android platform.
     * <p>
     * ANDROID
     */
    private Boolean isAndroid;
    /**
     * Sending {@code true} wakes your app to run custom native code (Apple interprets this as
     * {@code content-available=1}). Omit {@link #contents} field to make notification silent.
     * <p>
     * iOS
     */
    private Boolean content_available;
    /**
     * The notification's subtitle, a map of language codes to text for each language. Each hash must have a language
     * code string for a key, mapped to the localized text you would like users to receive for that language. A default
     * title may be displayed if a title is not provided.
     * <p>
     * Example: {@code {"en": "English Subtitle", "es": "Spanish Subtitle"}}
     * <p>
     * iOS 10+
     */
    private Map<String, String> subtitle;
    /**
     * Sending {@code true} allows you to change the notification content in your app before it is displayed. Triggers
     * {@code didReceive(_:withContentHandler:)} on your {@code UNOneSignalServiceExtension}.
     * <p>
     * iOS 10+
     */
    private Boolean mutable_content;
    /**
     * Adds media attachments to notifications. Set as JSON object, key as a media id of your choice and the value as a
     * valid local file name or URL. User must press and hold on the notification to view.
     * <p>
     * Do not set {@link #mutable_content} to download attachments. The core.OneSignal SDK does this automatically.
     * <p>
     * Example: {@code {"id1": "https://domain.com/image.jpg"}}
     * <p>
     * iOS 10+
     */
    private Map<String, String> ios_attachments;
    /**
     * Category APS payload, use with {@code registerUserOneSignalSettings:categories} in your Objective-C / Swift
     * code.
     * <p>
     * Example: {@code calendar} category which contains actions like {@code accept} and {@code decline}
     * <p>
     * iOS 10+ This will trigger your {@code UNOneSignalContentExtension} whose ID matches this category.
     * <p>
     * iOS
     */
    private String ios_category;
    /**
     * Sound file that is included in your app to play instead of the default device notification sound. Pass "nil" to
     * disable vibration and sound for the notification.
     * <p>
     * Example: {@code "notification.wav"}
     * <p>
     * iOS
     */
    private String ios_sound;
    /**
     * Describes whether to set or increase/decrease your app's iOS badge count by the {@link #ios_badgeCount} specified
     * count.
     * <p>
     * iOS
     */
    private BadgeType ios_badgeType;
    /**
     * Used with BadgeType, describes the value to set or amount to increase/decrease your app's iOS badge
     * count by.
     * <p>
     * You can use a negative number to decrease the badge count when used with an BadgeType} of
     * {@link BadgeType#INCREASE}.
     * <p>
     * iOS
     */
    private Integer ios_badgeCount;
    /**
     * Picture to display in the expanded view. Can be a drawable resource name or a URL.
     * <p>
     * ANDROID
     */
    private String bigPicture;
    /**
     * Allows setting a background image for the notification.
     * <p>
     * ANDROID
     */
    private AndroidBackgroundLayout android_background_layout;
    /**
     * If blank the app icon is used. Must be the drawable resource name.
     * <p>
     * ANDROID
     */
    private String small_icon;
    /**
     * If blank the {@link #small_icon} is used. Can be a drawable resource name or a URL.
     * <p>
     * ANDROID
     */
    private String large_icon;
    /**
     * Sound file that is included in your app to play instead of the default device notification sound.
     * NOTE: Leave off file extension for Android.
     * <p>
     * Example: {@code "notification"}
     * <p>
     * ANDROID
     */
    private String android_sound;
    /**
     * Sets the background color of the notification circle to the left of the notification text.
     * Only applies to apps targeting Android API level 21+ on Android 5.0+ devices.
     * <p>
     * Example(Red): {@code "FFFF0000"}
     * <p>
     * ANDROID
     */
    private String android_accent_color;
    /**
     * All notifications with the same group will be stacked together using Android's core.OneSignal Stacking feature.
     * <p>
     * ANDROID
     */
    private String android_group;

    private OneSignal (String appId, String apiKey)
    {
        this.setAppId(appId);
        this.apiKey = apiKey;
    }

    public static OneSignal builder (String appId, String apiKey)
    {
        return new OneSignal(appId, apiKey);
    }

    public Object push (boolean systemOut)
    {
        try
        {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic " + apiKey);
            con.setRequestMethod("POST");
            apiKey = null;
            String request = Reflex.flat(this);
            byte[] sendBytes = request.getBytes();
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            if (httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST)
            {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else
            {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }

            if (systemOut)
            {
                System.out.println("core.OneSignal HTTP Request Body:");
                System.out.println(request);
                System.out.println("core.OneSignal HTTP Response Code: " + httpResponse);
                System.out.println("core.OneSignal HTTP Response Body:");
                System.out.println(jsonResponse);
            }

            return jsonResponse;
        }
        catch (Throwable t)
        {
            return t;
        }
    }

    public Object push ()
    {
        return push(true);
    }

    private OneSignal setAppId (String id)
    {
        this.app_id = id;
        return this;
    }

    public OneSignal setUrl (String url)
    {
        this.url = url;
        return this;
    }

    public OneSignal addPlayerIds (String... ids)
    {
        if (include_player_ids == null)
            include_player_ids = new ArrayList<>();

        Collections.addAll(include_player_ids, ids);
        return this;
    }

    public OneSignal addPlayerIds (List<String> ids)
    {
        if (include_player_ids == null)
            include_player_ids = new ArrayList<>();

        include_player_ids.addAll(ids);
        return this;
    }

    public List<String> getIncludePlayerIds ()
    {
        if (include_player_ids == null)
            include_player_ids = new ArrayList<>();

        return include_player_ids;
    }

    public OneSignal addIncludedSegments (String... segments)
    {
        if (included_segments == null)
            included_segments = new ArrayList<>();

        Collections.addAll(included_segments, segments);
        return this;
    }

    public List<String> getIncludedSegments ()
    {
        if (included_segments == null)
            included_segments = new ArrayList<>();

        return included_segments;
    }

    public OneSignal addExcludedegments (String... excluded)
    {
        if (excluded_segments == null)
            excluded_segments = new ArrayList<>();

        Collections.addAll(excluded_segments, excluded);
        return this;
    }

    public List<String> getExcludedSegments ()
    {
        if (excluded_segments == null)
            excluded_segments = new ArrayList<>();

        return excluded_segments;
    }

    public OneSignal addHeadings (String lang, String heading)
    {
        if (headings == null)
            headings = new HashMap<>();

        this.headings.put(lang, heading);
        return this;
    }

    public OneSignal addContents (String lang, String content)
    {
        if (contents == null)
            contents = new HashMap<>();

        this.contents.put(lang, content);
        return this;
    }

    public OneSignal addData (String key, String value)
    {
        if (data == null)
            data = new HashMap<>();

        data.put(key, value);
        return this;
    }

    public OneSignal setButtons (List<Button> buttons)
    {
        this.buttons = buttons;
        return this;
    }

    public OneSignal setCollapseId (String id)
    {
        this.collapse_id = collapse_id;
        return this;
    }

    public OneSignal setTtl (Integer ttl)
    {
        this.ttl = ttl;
        return this;
    }

    public OneSignal setPriority (Integer priority)
    {
        this.priority = priority;
        return this;
    }

    public OneSignal isIOS (Boolean enable)
    {
        isIos = enable;
        return this;
    }

    public OneSignal isAndroid (Boolean enable)
    {
        isAndroid = enable;
        return this;
    }

    public OneSignal setContentAvailable (Boolean available)
    {
        this.content_available = available;
        return this;
    }

    public OneSignal setSubtitle (Map<String, String> subtitle)
    {
        this.subtitle = subtitle;
        return this;
    }

    public OneSignal setMutableContent (Boolean content)
    {
        this.mutable_content = content;
        return this;
    }

    public OneSignal setIOSAttachments (Map<String, String> attachments)
    {
        this.ios_attachments = attachments;
        return this;
    }

    public OneSignal setIOSCategory (String category)
    {
        this.ios_category = category;
        return this;
    }

    public OneSignal setIOSSound (String sound)
    {
        this.ios_sound = sound;
        return this;
    }

    public OneSignal setIOSBadgeType (BadgeType type)
    {
        this.ios_badgeType = type;
        return this;
    }

    public OneSignal setIOSBadgeCount (Integer count)
    {
        this.ios_badgeCount = count;
        return this;
    }

    public OneSignal setBigPicture (String bigPicture)
    {
        this.bigPicture = bigPicture;
        return this;
    }

    public OneSignal setAndroidBackgroundLayout (AndroidBackgroundLayout layout)
    {
        this.android_background_layout = layout;
        return this;
    }

    public OneSignal setSmallIcon (String icon)
    {
        this.small_icon = icon;
        return this;
    }

    public OneSignal setLargeIcon (String icon)
    {
        this.large_icon = icon;
        return this;
    }

    public OneSignal setAndroidSound (String sound)
    {
        this.android_sound = sound;
        return this;
    }

    public OneSignal setAndroidAccentColor (String color)
    {
        this.android_accent_color = color;
        return this;
    }

    public OneSignal setAndroidGroup (String group)
    {
        this.android_group = group;
        return this;
    }

    public enum BadgeType
    {

        /**
         * Leaves the count unaffected.
         */
        NONE("None"),

        /**
         * Directly sets the badge count to the number specified in core.OneSignal#ios_badgeCount.
         */
        SET_TO("SetTo"),

        /**
         * Adds the number specified in core.OneSignal#ios_badgeCount to the total. Use a negative number to decrease
         * the badge count.
         */
        INCREASE("Increase");

        private final String value;

        BadgeType (String value)
        {
            this.value = value;
        }

        @Override public String toString ()
        {
            return value;
        }
    }

    private static class Button
    {
        /**
         * Button ID.
         */
        private String id;

        /**
         * Button text.
         */
        private String text;

        /**
         * Button icon. Only works for Android.
         */
        private String icon;

        /**
         * Button url. Only works for Chrome 48+.
         */
        private String url;

        public Button setId (String id)
        {
            this.id = id;
            return this;
        }

        public Button setText (String text)
        {
            this.text = text;
            return this;
        }

        public Button setIcon (String icon)
        {
            this.icon = icon;
            return this;
        }

        public Button setUrl (String url)
        {
            this.url = url;
            return this;
        }
    }

    private static class AndroidBackgroundLayout
    {
        /**
         * Asset file, android resource name, or URL to remote image.
         */
        private String image;

        /**
         * Title text color ARGB Hex format. Example(Blue): {@code "FF0000FF"}.
         */
        private String headings_color;

        /**
         * Body text color ARGB Hex format. Example(Red): {@code "FFFF0000"}
         */
        private String contents_color;


        public AndroidBackgroundLayout setImage (String image)
        {
            this.image = image;
            return this;
        }

        public AndroidBackgroundLayout setHeadingsColor (String color)
        {
            this.headings_color = color;
            return this;
        }

        public AndroidBackgroundLayout setContentsColor (String color)
        {
            this.contents_color = color;
            return this;
        }
    }
}
