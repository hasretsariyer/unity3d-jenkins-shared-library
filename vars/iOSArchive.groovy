def call(String provisioning_profile_file_path = "", String archivePath = "./outputFolder/jenkins-test.xcarchive") {
    script {
        sh "security cms -D -i $provisioning_profile_path >> temp.plist"
        def PROVISIONING_PROFILE_SPECIFIER = sh(script: '/usr/libexec/PlistBuddy -c \'print ":Name"\' temp.plist | xargs echo -n', returnStdout: true)
        def UUID = sh(script: '/usr/libexec/PlistBuddy -c \'print ":UUID"\' temp.plist | xargs echo -n', returnStdout: true)
        def TEAM_ID = sh(script: '/usr/libexec/PlistBuddy -c \'print ":TeamIdentifier"\' temp.plist |  sed -e 1d -e \'$d\' | xargs echo -n', returnStdout: true)
        
        sh """
            ruby updateExportOptions.rb ./temp.plist ./exportOptions.plist
            cat ./exportOptions.plist
            
            echo "Create Archive starting..."
            /usr/bin/xcodebuild -project ./iOSProj/Unity-iPhone.xcodeproj -scheme Unity-iPhone -sdk iphoneos -configuration Release archive \
                -archivePath ${archivePath} clean CODE_SIGN_STYLE=Manual  \
                COMPILER_INDEX_STORE_ENABLE=NO CODE_SIGN_IDENTITY="iPhone Distribution" \
                PROVISIONING_PROFILE_SPECIFIER="${PROVISIONING_PROFILE_SPECIFIER}" \
                DEVELOPMENT_TEAM=${TEAM_ID} EXPANDED_CODE_SIGN_IDENTITY="" CODE_SIGNING_REQUIRED="NO" CODE_SIGNING_ALLOWED="NO" \
                PROVISIONING_PROFILE=${UUID} 
            echo "Create Archive finished..."
        """
    }
}
