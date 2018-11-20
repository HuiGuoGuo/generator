function generatorProject() {
    var groupId = $('input[name=groupId]').val();
    var artifactId = $('input[name=artifactId]').val();
    var version = $('input[name=version]').val();
    var moduleName = $('input[name=moduleName]').val();
    window.location.href = 'sys/generator/project?groupId=' + groupId + '&artifactId=' + artifactId + '&version=' + version + '&moduleName=' + moduleName;
}

$("input[name='artifactId']").on('change', function () {
    console.log($(this).val());
    $("input[name='moduleName']").val($(this).val());
});


