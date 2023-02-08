## Gatling Recorder Settings/Usage
### Basic Settings
- **Package:** com.gatling.tests
- **Class Name:** *Doesn't Matter*
- Keep checkboxes as they are
- **Encoding:** Unicode (UTF-8)
- Select *No static resources* on bottom-right
  - Add: `.*\.PNG`, `.*/pic/`, `.*\.svg`, `.*/js/site.js.*`, `.*/hub/notificationhub/.*` to list

### HAR Converter Mode
1. Open application that you want to test
2. Open developer settings on browser: more tools -> developer settings
   1. Go to network tab
   2. Make sure "Preserve log" is checked and record is on
   3. Clear settings if needed
3. Restart application
4. Interact with application as needed
5. Stop recording
6. Save steps as HAR file
7. Upload to Gatling Recorder (make sure HAR is selected as "Recorder mode")
8. Click start and Scala script will be created in given directory

### Proxy Mode
1. Open Gatling recorder and make sure you are on "Proxy mode"
2. Set port to correct port in recorder
3. Enable proxy on browser
   1. Go to settings
   2. Search from proxy settings
   3. Input correct proxy port
4. If https website
   1. Set HTTPS mode to "Certificate Authority"
   2. Generate certificate
   3. Add certificate to browser settings
5. Go to desired website
6. Click on "Start" button
7. Complete the desired actions
   1. Optional: Add tags to define sections of recording
8. Click "Stop & Save" to end recording
9. Testing script will be created