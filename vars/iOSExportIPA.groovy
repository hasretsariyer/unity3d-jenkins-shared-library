def call(String archivePath = "./outputFolder/jenkins-test.xcarchive", String exportFolder="./outputFolder") {
    script {
        sh """
            echo "Export ios ipa starting..."
            /usr/bin/xcodebuild -exportArchive -archivePath ${archivePath} -exportPath ${exportFolder} -exportOptionsPlist exportOptions.plist
            echo "Export ios ipa finished..."
        """
    }
}
