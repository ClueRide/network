This is a Java SE module for importing GPX, GeoJSON, and perhaps GML 
files into a Spatially-enabled PostGreSQL database (PostGIS).

It uses CDI (supported by Weld) to match up with injection of resources,
but the JPA persistence is done by providing the EntityManager directly
rather than letting the EE container provide this. This means we don't
use the @PersistenceContext annotation in this project, but much of the 
other JPA code is supported.
