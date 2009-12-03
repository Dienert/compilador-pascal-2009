{
  er-sem21.s
      
  Variavel nao declarada com mesmo nome que variavel local de um
  procedimento anterior
  }
program erro21;      
procedure m;
  var i : integer;
  begin
  end

procedure p;
  begin
  i:= 0
  end

begin
end.
