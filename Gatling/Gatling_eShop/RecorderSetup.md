## Gatling Recorder Settings/Usage
### Basic Settings
- **Package:** com.gatling.tests
- **Class Name:** *Doesn't Matter*
- Keep checkboxes as they are
- **Encoding:** Unicode (UTF-8)
- Select *No static resources* on bottom-right
  - Add: `.*\.PNG`, `.*/pic/`, `.*\.svg`, `.*/js/site.js.*`, `.*/hub/notificationhub/.*` to list

### HAR Converter Usage
1. Open application to test
2. Open developer settings: more tools -> developer settings
   1. Go to network tab
   2. Make sure "Preserve log" is checked and record is on
   3. Clear settings if needed
3. Restart application
4. Interact with application as needed
5. Stop recording
6. Save steps as HAR file
7. Upload to Gatling Recorder (make sure HAR is selected as "Recorder mode")
8. Click start and Scala script will be created