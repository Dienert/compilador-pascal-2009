{
  ok-sem01.s
      
  Testa se valores booleanos podem ser utilizados com operadores
  de comparacao
  }
program ok01;
    procedure m();
      begin
      if true >= false
      then
        writeln(0);
      end;

begin
m();
end.
