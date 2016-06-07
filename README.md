# Android-CircleMenuWithScaleEffect
A circle menu which the menu item can scale by degree 
# ScreenShots

![image description](http://a2.qpic.cn/psb?/V146Ouyu1PxDav/SfQRf2AhgUjogtmjRPPvfrwTQwI9lVvCc89eO8xEfWE!/b/dKkAAAAAAAAA&bo=1wGjAtcBowICbEg!&rf=viewer_4)
# Description
This projecet is inspirat by another project [szugyi/Android-CircleMenu](https://github.com/szugyi/Android-CircleMenu)

you can just copy  "RippleCircle.java" and "circleLayout.java" to your project,

copy circleLayout reference in your xml;

like this:

```
    <com.example.susan.balldemo.CircleLayout
        android:id="@+id/circle_layout"
        android:layout_width="match_parent"
        android:layout_height="400dp"
        android:layout_above="@+id/selected_textView"
        android:layout_gravity="center">
        <include layout="@layout/menu_item" />
    </com.example.susan.balldemo.CircleLayout>

```

menu_item.xml
```
<?xml version="1.0" encoding="utf-8"?>
<merge xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:ripplecircle="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.example.susan.balldemo.RippleCircle
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        ripplecircle:centre_text="本周"
        ripplecircle:circle_color="#218A8A"
        ripplecircle:text_size="27" />

    <com.example.susan.balldemo.RippleCircle
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        ripplecircle:centre_text="本月"
        ripplecircle:text_size="27" />
        
    <com.example.susan.balldemo.RippleCircle
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        ripplecircle:centre_text="今天"
        ripplecircle:circle_color="#08153F"
        ripplecircle:text_size="27" />

    <!--ripplecircle:centre_text="本月"-->
    <!--ripplecircle:circle_color="#000000"-->
    <!--ripplecircle:text_size="45"-->


</merge>
```
the circle layout will autolayout your items.

## some attrs

`ripplecircle:centre_text` --  circle item centre text.

`circle_color`--circle item color

`text_size` -- circle item centre text size .


### This project With NO Screen Adpter  
1 . There Threee type of Item Scale :

 `    Float MAXScale = 1f, MIDScale = 0.8f, MINScale = 0.75f;`
 
you can change it in "CircleLayout.java   Line:90"

recommand Scale Value from 0-1:if the scale value bigger than 1,the ripple Cirle item may out layout!

meaning--may not show a certain circle view.

2 .bigger item 

you can change it in "CircleLayout.java   Line:89"

` int selfDesinWidth = (int) getContext().getResources().getDisplayMetrics().density * 120;`

change the `120` to your perfer number!

3 . rotation direction 
if you wanna 
    
    --the item only clockwise rotation
    "CircleLayout.java  Line:262 to 264"`commented out `
```
   if (destAngle < 0) {
               destAngle += 360;
          }
```
    --the item only counterclockwise rotation
    "CircleLayout.java  Line:266 to 268"`commented out `
```
    if (destAngle > 180) {
                destAngle = -1 * (360 - destAngle);
            }
```
4 .NO rotateButtons with your figner gesture.

 "CircleLayout.java  Line:266 to 453"`commented out `
 
```
 rotateButtons((float) (touchStartAngle - currentAngle));
```
### EventListeners
- OnItemSelectedListener:wil show  text content of first item(selectedView)
- OnItemClickListener:after first item selected click answer

 
