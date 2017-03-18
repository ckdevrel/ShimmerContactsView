# ShimmerContactsView
Loading contacts wouldn't be easier than ShimmerContactsView


Demo I |      Demo II
-------- | ---
<a href="http://imgur.com/hY0n7QG"><img src="http://i.imgur.com/hY0n7QG.gif" title="source: imgur.com" height="480" width="260" /></a> | <a href="http://imgur.com/nvMdUJI"><img src="http://i.imgur.com/nvMdUJI.gif" title="source: imgur.com" height="480" width="260" /></a>

### Attributes and Methods

Following are the attributes and methods to initialise the demo views.

| XML Attributes | Java Methods | Explanation |
| -------------  | ------------ | ----------- | 
|```app:demo_child_count``` | ```setDemoChildCount(int)``` | Integer value that sets the number of demo views should be present in shimmer adapter |
|```app:demo_layout``` | ```setDemoLayoutReference(int)``` | Layout reference to your demo view. Define your my_demo_view.xml and refer the layout reference here. |
|```app:demo_layout_manager_type``` | ```setDemoLayoutManager(LayoutManagerType)``` | Layout manager of demo view. Can be one among linear_veritical or linear_horizontal or grid. |



Usage
--------

Define your xml as:

```xml
  
  <com.takeoffandroid.shimmercontactsview.views.shimmer.ShimmerRecyclerView
      android:id="@+id/recycler_view_contacts"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:scrollbars="vertical"
      app:demo_child_count="10"
      app:demo_layout="@layout/shimmer_sample_view"
      app:demo_layout_manager_type="linear_vertical"
      />

```
where ```@layout/layout_demo_grid``` refers to your sample layout that should be shown during loading spinner. Now on your activity onCreate, initialize the shimmer as below:

```java
ShimmerRecyclerView shimmerRecycler = (ShimmerRecyclerView) findViewById(R.id.recycler_view_contacts);
shimmerRecycler.showShimmerAdapter();
```

Benchmark Test
---------------


<a href="http://imgur.com/xytbtud"><img src="http://i.imgur.com/xytbtud.png" title="source: imgur.com" height="450" width="600" /></a>


Acknowledgements
-----------------

* Facebook for <a href="https://github.com/facebook/shimmer-android">Shimmer Android</a>
* Thanks to https://github.com/sharish/ShimmerRecyclerView

