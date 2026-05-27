export function somenteDigitos(valor: string): string {
  return valor.replace(/\D/g, '');
}

export function validarCpfBasico(cpf: string): boolean {
  const d = somenteDigitos(cpf);
  if (d.length !== 11) return false;
  if (/^(\d)\1+$/.test(d)) return false;

  let soma = 0;
  for (let i = 0; i < 9; i++) soma += parseInt(d[i]) * (10 - i);
  let dig1 = 11 - (soma % 11);
  if (dig1 >= 10) dig1 = 0;
  if (dig1 !== parseInt(d[9])) return false;

  soma = 0;
  for (let i = 0; i < 10; i++) soma += parseInt(d[i]) * (11 - i);
  let dig2 = 11 - (soma % 11);
  if (dig2 >= 10) dig2 = 0;
  return dig2 === parseInt(d[10]);
}

export function validarTelefoneBasico(telefone: string): boolean {
  return /^\d{10,11}$/.test(somenteDigitos(telefone));
}

export function validarPlacaBasica(placa: string): boolean {
  const placaNormalizada = placa.trim().toUpperCase();
  return /^[A-Z]{3}-?\d[A-Z0-9]\d{2}$/.test(placaNormalizada);
}

export function validarEmailBasico(email: string): boolean {
  if (!email) return true; // email é opcional, só valida se preenchido
  return /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/.test(email);
}
